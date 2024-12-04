package com.ns01.ns01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ns01.ns01.Exceptions.MyException;
import com.ns01.ns01.Repository.RatingRepository;
import com.ns01.ns01.config.Configuration;
import com.ns01.ns01.model.AdminResponse;
import com.ns01.ns01.model.TravelRating;

@Controller
public class AdminContorller {

    @Autowired
    RatingRepository ratingRepository;

    @GetMapping("/adminHome")
    public String adminHome(Model model) throws MyException {
        model.addAttribute("home", "home");
        return "Admin";

    }

    @GetMapping("/users")
    public String usersRecords(Model model) throws MyException {

        AdminResponse rm = null;
        try {
            rm = Configuration
                    .getRestTemplate()
                    .getForObject(Configuration.getIP() + "admin/getUsers", AdminResponse.class);
        } catch (Exception e) {
            throw new MyException("your server is not reachable");
        }

        model.addAttribute("userspage", "users");
        model.addAttribute("users", rm.getUserModels());

        return "Admin";

    }

    @GetMapping("/files")
    public String userFiles(Model model) throws MyException {

        AdminResponse rm = null;
        try {
            rm = Configuration
                    .getRestTemplate()
                    .getForObject(Configuration.getIP() + "admin/getFiles", AdminResponse.class);
        } catch (Exception e) {
           // throw new MyException("your server is not reachable");
            throw new MyException("error in server");
        }

        model.addAttribute("filespage", "filespage");
        model.addAttribute("files", rm.getFileModels());

        return "Admin";

    }

     @GetMapping("/addRatingForm")
    public String addSimRating1(Model model) throws MyException {

       

        model.addAttribute("filespage", "filespage");
       // model.addAttribute("files", rm.getFileModels());

        return "Admin";

    }

    @GetMapping("/addRating")
    public String addSimRating(Model model,@RequestParam("dest1") String dest1, @RequestParam("dest2")String dest2,
    @RequestParam("rating")String rating) throws MyException {

        System.out.println(dest1+dest2+rating);
        TravelRating tv=new TravelRating();
        tv.setDest1(dest1);tv.setDest2(dest2);tv.setRating(Double.parseDouble(rating));
       TravelRating tv1= ratingRepository.save(tv);
       if(tv1==null){
        System.out.println("failed to insert");
       }else{
        System.out.println("inserted");
       }
        

        model.addAttribute("filespage", "filespage");
       // model.addAttribute("files", rm.getFileModels());

        return "Admin";

    }

    @GetMapping("/endPointCheck")
    public String harmFiles(Model model) throws MyException {

        AdminResponse rm = null;
        try {
            rm = Configuration
                    .getRestTemplate()
                    .getForObject(Configuration.getIP() + "admin/checkEndPoints", AdminResponse.class);
        } catch (Exception e) {
            throw new MyException("your server is not reachable");
        }

        model.addAttribute("endPoint","endPoint");
        model.addAttribute("endpoints", rm.getEndpointsModels());

        return "Admin";

    }

}
