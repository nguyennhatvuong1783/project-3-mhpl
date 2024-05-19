/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.entity.ThietBi;
import com.project3mhpl.entity.ThongTinSD;
import com.project3mhpl.service.ThanhVienService;
import com.project3mhpl.service.ThietBiService;
import com.project3mhpl.service.ThongTinSDService;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Controller
public class ThietBiController {

    @Autowired
    private ThietBiService thietBiService;

    @Autowired
    private ThanhVienService thanhVienService;

    @Autowired
    private ThongTinSDService thongTinSDService;

    @GetMapping("/dat-muon-thiet-bi")
    public String getAll(Model m, HttpServletRequest request) {
        Date currentDate = new Date();
        Boolean isAuthenticated = thanhVienService.checkAuth(request);

        m.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated == false) {
            return "redirect:sign-in";
        }
        m.addAttribute("isAdmin", thanhVienService.checkAdmin(request));

        Iterable<ThietBi> list = thietBiService.getAll();
        ArrayList<ThietBi> products = new ArrayList<>();
        for (ThietBi t : list) {
            products.add(t);
        }

        m.addAttribute("data", products);
        m.addAttribute("currentDate", currentDate);
        return "thiet_bi_all";
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/dat-muon-thiet-bi", method = RequestMethod.POST)
    @ResponseBody
    public String submitData(@RequestParam("date") String date, @RequestParam("idTB") int maTB, Model m,
            HttpServletRequest request) throws ParseException {
        Boolean isAuthenticated = thanhVienService.checkAuth(request);

        m.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated == false) {
            return "redirect:sign-in";
        }

        // Xử lý đặt mượn
        List<ThongTinSD> list = thongTinSDService.getTTSDByIdTB(maTB);
        Calendar calendar = Calendar.getInstance();
        Date tgDatCho = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
        calendar.setTime(tgDatCho);
        int ngayDatCho = calendar.get(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTgDatcho() != null) {
                calendar.setTime(list.get(i).getTgDatcho());
                int ngayDatChoTTSD = calendar.get(Calendar.DAY_OF_MONTH);

                if (ngayDatCho == ngayDatChoTTSD) {
                    return "alertDatCho";
                }
            } else {
                calendar.setTime(list.get(i).getTgMuon());
                int ngayMuon = calendar.get(Calendar.DAY_OF_MONTH);

                if (list.get(i).getTgTra() == null && ngayDatCho == ngayMuon) {
                    return "alertMuon";
                }
            }
        }

        Iterable<ThongTinSD> ThongTinSDIterable = thongTinSDService.getAll();
        int maTT = 0;
        for (ThongTinSD thongTinSD : ThongTinSDIterable) {
            maTT++;
        }
        maTT++;

        ThanhVien tv = thanhVienService.getProfile(request);
        Optional<ThietBi> thietBiOptional = thietBiService.getByID(maTB);
        ThietBi thietBi = thietBiOptional.get();
        ThongTinSD thongTinSD = new ThongTinSD(maTT, tv, thietBi, tgDatCho, null, null, null);

        thongTinSDService.store(thongTinSD);

        return "modal";
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/muonthietbi", method = RequestMethod.POST)
    @ResponseBody
    public String submitData2(@RequestParam("matv") int maTV, @RequestParam("matb") int maTB, Model m,
            HttpServletRequest request) throws ParseException {
        Boolean isAuthenticated = thanhVienService.checkAuth(request);

        m.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated == false) {
            return "redirect:sign-in";
        }

        // Xử lý mượn
        List<ThongTinSD> list = thongTinSDService.getTTSDByIdTB(maTB);
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();

        ThongTinSD oldThongTinSD = new ThongTinSD();
        boolean flag = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTgDatcho() != null) {
                calendar.setTime(list.get(i).getTgDatcho());
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                Date tgDatChoPlus1h = calendar.getTime();
                int NgayDatCho = calendar.get(Calendar.DAY_OF_MONTH);

                calendar.setTime(currentDate);
                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

                if (currentDate.compareTo(tgDatChoPlus1h) <= 0 && currentDay == NgayDatCho) {
                    if (maTV == list.get(i).getThanhVienTTSD().getMaTV()) {
                        oldThongTinSD = list.get(i);
                        flag = true;
                        break;
                    }
                    return "alertDatcho";
                }
            } else {
                if (list.get(i).getTgTra() == null) {
                    return "alert";
                }
            }
        }

        // Insert DB
        if (flag == true) {
            oldThongTinSD.setTgMuon(currentDate);
            thongTinSDService.store(oldThongTinSD);
            return "ok";
        }

        Iterable<ThongTinSD> ThongTinSDIterable = thongTinSDService.getAll();
        int maTT = 0;
        for (ThongTinSD thongTinSD : ThongTinSDIterable) {
            maTT++;
        }
        maTT++;

        Optional<ThanhVien> tvOptinonal = thanhVienService.getById(maTV);
        ThanhVien tv = tvOptinonal.get();
        Optional<ThietBi> thietBiOptional = thietBiService.getByID(maTB);
        ThietBi thietBi = thietBiOptional.get();
        ThongTinSD thongTinSD = new ThongTinSD(maTT, tv, thietBi, null, null, currentDate, null);

        thongTinSDService.store(thongTinSD);
        return "ok";
    }

    @RequestMapping(value = "/trathietbi", method = RequestMethod.POST)
    @ResponseBody
    public String traThietBi(@RequestParam("matv") int maTV, @RequestParam("matb") int maTB, Model m, HttpServletRequest request) throws ParseException {
        Boolean isAuthenticated = thanhVienService.checkAuth(request);
        m.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated == false) {
            return "redirect:sign-in";
        }

        // Xử lý trả
        List<ThongTinSD> list = thongTinSDService.getTTSDByIdTB(maTB);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getThanhVienTTSD().getMaTV() == maTV) {
                if (list.get(i).getTgMuon() != null && list.get(i).getTgTra() == null) {
                    Date currentDate = new Date();
                    ThongTinSD thongTinSD = list.get(i);
                    thongTinSD.setTgTra(currentDate);
                    thongTinSDService.store(thongTinSD);
                    return "ok";
                }
            }
        }

        return "alert";
    }

    @RequestMapping(value = "/them-thiet-bi", method = RequestMethod.POST)
    @ResponseBody
    public String themThietBi(@RequestParam("matb") int maTB, @RequestParam("tentb") String tenTB,
            @RequestParam("motatb") String moTaTB, Model m, HttpServletRequest request) {
        Boolean isAuthenticated = thanhVienService.checkAuth(request);

        m.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated == false) {
            return "redirect:sign-in";
        }

        // Xử lý dữ liệu và trả về view hoặc thực hiện các hành động khác
        Iterable<ThietBi> list = thietBiService.getAll();
        for (ThietBi thietBi : list) {
            if (maTB == thietBi.getMaTB()) {
                return "alert";
            }
        }

        ThietBi thietBi = new ThietBi(maTB, tenTB, moTaTB, null);

        thietBiService.store(thietBi);

        return "modal";
    }

    @RequestMapping(value = "/xoa-thiet-bi", method = RequestMethod.DELETE)
    @ResponseBody
    public String xoaThietBi(@RequestParam("idTB") int maTB, Model m, HttpServletRequest request) {
        Boolean isAuthenticated = thanhVienService.checkAuth(request);

        m.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated == false) {
            return "redirect:sign-in";
        }

        // Xử lý dữ liệu và trả về view hoặc thực hiện các hành động khác
        Optional<ThietBi> thietBiOptional = thietBiService.getByID(maTB);
        ThietBi thietBi = thietBiOptional.get();
        thietBi.setStatus(0);

        thietBiService.store(thietBi);

        return "modal";
    }

    @GetMapping("/manage-devices")
    public String manageDevicesPage(Model m, HttpServletRequest request) {
        Boolean isAuthenticated = thanhVienService.checkAuth(request);
        Boolean isAdmin = thanhVienService.checkAdmin(request);

        m.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated == false) {
            return "redirect:sign-in";
        }
        m.addAttribute("isAdmin", isAdmin);
        if (isAdmin == false) {
            return "redirect:home";
        }
        Iterable<ThietBi> list = thietBiService.getAll();
        ArrayList<ThietBi> products = new ArrayList<>();
        for (ThietBi t : list) {
            products.add(t);
        }

        m.addAttribute("data", products);
        return "manage-devices";
    }

    @PostMapping("/import")
    public String importUsers(@RequestParam("file") MultipartFile file) throws IOException {

        thietBiService.importListTB(file);

        return "redirect:/dashboard#manage-devices";
    }
}
