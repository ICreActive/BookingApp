package com.shkubel.project.web;


import com.shkubel.project.exception.AppartmentValidationException;
import com.shkubel.project.exception.RoomNotFoundException;
import com.shkubel.project.models.entity.Room;
import com.shkubel.project.models.entity.KlassAppartament;
import com.shkubel.project.service.RoomService;
import com.shkubel.project.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    private final RoomService roomService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public CatalogController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public String hotels(Model model) {
        List<Room> rooms = roomService.findAllEnableRoom();
        model.addAttribute("rooms", rooms);
        return "rooms/catalog";
    }

    @GetMapping("/new")
    public String newHotel(Model model) {

        model.addAttribute("room", roomService.createRoom());
        model.addAttribute("klassAp", KlassAppartament.values());
        return "/rooms/new";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute("room") @Valid Room room, BindingResult bindingResult, @RequestPart("file") MultipartFile file, Model model) throws IOException {
//        if (file != null && !file.getOriginalFilename().isEmpty()) {
//            File uploadDir = new File(uploadPath);
//
//            if (!uploadDir.exists()) {
//                uploadDir.mkdir();
//            }
//            String uniqueFile = UUID.randomUUID().toString();
//            String resultFilename = uniqueFile + "." + file.getOriginalFilename();
//
//            file.transferTo(new File(uploadPath + "/" + resultFilename));
//
//            room.setFilename(resultFilename);
//        }
        if (bindingResult.hasErrors()) {
            return "/rooms/new";
        }

        try {
            ValidationUtil.validationHotel(room);
            roomService.saveRoom(room);
            return "redirect:/catalog";
        }
        catch (AppartmentValidationException e) {
            model.addAttribute("message", e.getMessage());
            return "/rooms/new";
        }
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("room", roomService.findRoomById(id));
        return "rooms/room";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long roomId,
                              Model model) {
        try {
            roomService.disableRoom(roomId);
        } catch (RoomNotFoundException e) {
            model.addAttribute("message", "Error, try again later");
            return "rooms/room";
        }
        return "redirect:/catalog";
    }

}
