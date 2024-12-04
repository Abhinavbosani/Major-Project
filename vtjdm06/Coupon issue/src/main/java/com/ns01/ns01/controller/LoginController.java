package com.ns01.ns01.controller;

import java.time.LocalDate;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ns01.ns01.Exceptions.MyException;
import com.ns01.ns01.Repository.EndPointRepository;
import com.ns01.ns01.Repository.UserStatusRepository;
import com.ns01.ns01.config.Configuration;
import com.ns01.ns01.model.EndpointsModel;
import com.ns01.ns01.model.UserModel;
import com.ns01.ns01.model.UserReport;
import com.ns01.ns01.utils.Infomation;
import com.ns01.ns01.utils.ResponseMessage;

@Controller
@SessionAttributes({ "um", "ur" })
public class LoginController {

    @Autowired
    UserStatusRepository userStatusRepository;
    @Autowired
    EndPointRepository endPointRepository;

    @PostMapping("/loginsubmit")
    public String userLogin(
            @RequestParam("emailid") String emailid,
            @RequestParam("password") String password, Model model, HttpSession session, HttpServletRequest request)
            throws MyException {

       
        if (emailid.equalsIgnoreCase("admin@gmail.com") && password.equalsIgnoreCase("admin")) {
            model.addAttribute("home", "home");
            return "Admin";
        } else {

            UserModel um = new UserModel();
            um.setEmailId(emailid);
            um.setPassword(password);
            ResponseMessage rm = null;
            try {

                EndpointsModel em=new EndpointsModel();
                em.setEndPointName("login");em.setEndpointUrl(Configuration.getIP()+"%%user/login");em.setStatus(0);
                endPointRepository.save(em);
                
                rm = Configuration
                        .getRestTemplate()
                        .postForObject(Configuration.getIP() + "user/login", um, ResponseMessage.class);
            } catch (Exception e) {
                throw new MyException("your server is not reachable");
            }
            if (rm.getMessage().equalsIgnoreCase("success")) {
                um = rm.getUserModel();

                UserReport ur = new UserReport();
                ur.setUserId(um.getUserId());
                ur.setLoginDateAndTime(LocalDate.now().toString());
                ur.setLoginStatus("1");
                ur = userStatusRepository.save(ur);
                model.addAttribute("suid", ur.getId());
                model.addAttribute("um", um);
                model.addAttribute("profile", null);
                return "index";
            } else {
                String info = Infomation.getErrorMessage("Emailid or password wrong");
                model.addAttribute("info", info);
                return "login";
            }

        }

    }

    @GetMapping("/profile/{userid}")
    public String checkProfile(@PathVariable String userid, Model model) {

        ResponseMessage rm=null;
        try{

            EndpointsModel em=new EndpointsModel();
            em.setEndPointName("profile");em.setEndpointUrl(Configuration.getIP()+"user/profile");em.setStatus(1);
            endPointRepository.save(em);
       rm = Configuration
                .getRestTemplate()
                .getForObject(Configuration.getIP() + "user/profile/" + userid, ResponseMessage.class);

                if (rm.getMessage().equalsIgnoreCase("success")) {
                    model.addAttribute("um", rm.getUserModel());
                    model.addAttribute("profile", "profile");
                    return "index";
                } else {
                    model.addAttribute("error", "Error Occured ");
                    model.addAttribute("profile", "profile");
                    return "index";
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "index";

      

    }

    @GetMapping("/logout")
    public String Logout() {
        return "login";
    }

}
