package com.shkubel.project.web;


import com.shkubel.project.models.entity.Hotel;
import com.shkubel.project.models.entity.KlassAppartament;
import com.shkubel.project.service.HotelService;
import com.shkubel.project.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/hotels")
public class CatalogController {

    private final HotelService hotelService;

    @Value("${upload.path}")
    private String uploadPath;

    public CatalogController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/catalog")
    public String hotels(Model model) {
        List<Hotel> hotels = hotelService.findAllHotel();
        model.addAttribute("hotels", hotels);
        return "/hotels/catalog";
    }

    @GetMapping("/new")
    public String newHotel(Model model) {

        model.addAttribute("hotel", hotelService.createHotel());
        model.addAttribute("klassAp", KlassAppartament.values());
        return "/hotels/new";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute("hotel") Hotel hotel, Model model, @RequestPart("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uniqueFile = UUID.randomUUID().toString();
            String resultFilename = uniqueFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            hotel.setFilename(resultFilename);
        }


        if (!ValidationUtil.validationHotel(hotel).equals("success")) {
            return "/hotels/new";
        }
        hotelService.saveHotel(hotel);
        return "redirect:/hotels/catalog";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("hotel", hotelService.findHotelById(id));
        return "hotels/hotelPage";
    }

}
