package com.shkubel.project.web;


import com.shkubel.project.models.entity.Hotel;
import com.shkubel.project.models.entity.KlassAppartament;
import com.shkubel.project.service.impl.HotelServiceImpl;
import com.shkubel.project.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hotels")
public class CatalogController {

    @Autowired
    HotelServiceImpl hotelService;

    @GetMapping("/catalog")
    public String hotels (Model model) {
        Iterable <Hotel> hotels = hotelService.findAllHotel();
        model.addAttribute("hotels", hotels);
        return "/hotels/catalog";
    }


    @GetMapping("/new")
    public String newHotel (Model model) {
        model.addAttribute("hotel", new Hotel());
        model.addAttribute("klassAp", KlassAppartament.values());
        return "/hotels/new";
    }
    @PostMapping("/new")
    public String add (@ModelAttribute("hotel") Hotel hotel, Model model) {
        if (!ValidationUtil.validationHotel(hotel)) {
            return "/hotels/new";
        }
        hotelService.saveHotel(hotel);
        return "redirect:/hotels/catalog";
    }


    @GetMapping("/{id}")
    public String show (@PathVariable("id") Long id, Model model) {
        model.addAttribute("hotel", hotelService.findHotelById(id));
        return "hotels/id";
    }

}
