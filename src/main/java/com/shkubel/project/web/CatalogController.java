package com.shkubel.project.web;


import com.shkubel.project.models.entity.Room;
import com.shkubel.project.models.entity.KlassAppartament;
import com.shkubel.project.service.RoomService;
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
@RequestMapping("/rooms")
public class CatalogController {

    private final RoomService roomService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public CatalogController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/catalog")
    public String hotels(Model model) {
        List<Room> rooms = roomService.findAllHotel();
        model.addAttribute("rooms", rooms);
        return "/hotels/catalog";
    }

    @GetMapping("/new")
    public String newHotel(Model model) {

        model.addAttribute("room", roomService.createHotel());
        model.addAttribute("klassAp", KlassAppartament.values());
        return "/hotels/new";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute("room") Room room, Model model, @RequestPart("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uniqueFile = UUID.randomUUID().toString();
            String resultFilename = uniqueFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            room.setFilename(resultFilename);
        }


        if (!ValidationUtil.validationHotel(room).equals("success")) {
            return "/hotels/new";
        }
        roomService.saveHotel(room);
        return "redirect:/rooms/catalog";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("room", roomService.findHotelById(id));
        return "hotels/hotelPage";
    }

}
