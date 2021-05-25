package com.shkubel.project.web;


import com.shkubel.project.models.Hotel;
import com.shkubel.project.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hotels")
public class CatalogController {

    @Autowired
    BookingService bookingService;
    @GetMapping("/catalog")
    public String hotels (Model model) {
        Iterable <Hotel> hotels = bookingService.findAllHotel();
        model.addAttribute("hotels", hotels);
        return "/hotels/catalog";
    }


    @GetMapping("/new")
    public String newHotel (Model model) {
        model.addAttribute("hotel", new Hotel());
        return "/hotels/new";
    }
    @PostMapping("/new")
    public String add (@ModelAttribute("hotel") Hotel hotel) {
        bookingService.saveHotel(hotel);
        return "redirect:/hotels/catalog";
    }
    @GetMapping("/{id}")
    public String show (@PathVariable("id") long id, Model model) {
        model.addAttribute("hotel", bookingService.findHotelById(id));
        return "hotels/id";
    }
//    @PostMapping("/{id}")
//    public String booking (@PathVariable("id") long id, @ModelAttribute("hotel") Hotel hotel, Model model) {
//        if (bookingService.booking(id, hotel)) {
//            return "redirect:/hotels/catalog";
//        }
//        model.addAttribute("message", "this hotel is already booked");
//        return "/hotels/id";
//    }

}
