package com.ns01.ns01.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ns01.ns01.Repository.EndPointRepository;
import com.ns01.ns01.Repository.RatingRepository;
import com.ns01.ns01.config.Configuration;
import com.ns01.ns01.model.CategoryCluster;
import com.ns01.ns01.model.CustomerModel;
import com.ns01.ns01.model.EndpointsModel;
import com.ns01.ns01.model.FoodReceipeModel;
import com.ns01.ns01.model.Gender;
import com.ns01.ns01.model.RecRating;
import com.ns01.ns01.model.TravelRating;
import com.ns01.ns01.model.UserModel;
import com.ns01.ns01.utils.Infomation;
import com.ns01.ns01.utils.ResponseMessage;
import com.ns01.ns01.utils.TravelRecommendationSystem;

@Controller
public class UserController {

    @Autowired
    EndPointRepository endPointRepository;

    @Autowired
     RatingRepository ratingRepository;

     String filePath="C:\\Users\\91630\\OneDrive\\Desktop\\major project\\csvjson.json";

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    @GetMapping("/")
    public String test(HttpSession session) {
    
        return "login";
      
    }

    @GetMapping("registerForm")
    public String registerFrom() {
        return "register";
    }

    @GetMapping("/readJson1")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public String readJson(Model model,@RequestParam("receipe") String receipe){

        System.out.println("entered"+receipe);
        List<FoodReceipeModel> result=new ArrayList<>();

      ObjectMapper mapper = new ObjectMapper();
      try{

        List<FoodReceipeModel> customer = mapper.readValue(new File("C:/Users/shyam/Downloads/recipes.json"), List.class);
        System.out.println(customer.size());
      
        List<FoodReceipeModel> pojos = mapper.convertValue(
    customer,
    new TypeReference<List<FoodReceipeModel>>() { });
    System.out.println(pojos.size());
      for(FoodReceipeModel frm:pojos){
        if(frm.getName().contains(receipe)){
            System.out.println(frm);
            
            List<String> str=frm.getIngredients();
            System.out.println("ingredddd"+str.get(0));

            result.add(frm);
        }
      }
           
    
    }catch(Exception e){
        e.printStackTrace();

      }
      
       model.addAttribute("result",result);
        model.addAttribute("ratingPage", "ratingPage");
        return "index";
    }

    @PostMapping("/searchRec")
    public String searchRec(Model model,@RequestParam("travel") String place,@RequestParam("rating") String rating){

    System.out.println(place+rating);

    TravelRecommendationSystem trs=new TravelRecommendationSystem();
    trs.addUserPreference("1", place, Double.parseDouble(rating));
    
        List<TravelRating> ratings=ratingRepository.findAll();
        
        for(TravelRating tr:ratings){

            trs.addDestinationSimilarity(tr.getDest1(), tr.getDest2(), tr.getRating());

        }

       List<RecRating> rec= trs.getRecommendations("1");
       System.out.println(rec.toString());

        model.addAttribute("result",rec);
        model.addAttribute("ratingPage", "ratingPage");
        return "index";

    }

    @PostMapping("/register")
    public String userRegister(
     @RequestParam("name") String userName,
     @RequestParam("mobile")String mobile,
     @RequestParam("emailid")String emailid,
     @RequestParam("password")String password, Model model){

        UserModel um= new UserModel();
        um.setName(userName);um.setMobile(mobile);um.setEmailId(emailid);um.setPassword(password);
        ResponseMessage rm=Configuration
        .getRestTemplate()
        .postForObject(Configuration.getIP()+"user/Register", um, ResponseMessage.class);

   EndpointsModel em=new EndpointsModel();
   em.setEndPointName("register");em.setEndpointUrl(Configuration.getIP()+"&&user/Register");em.setStatus(0);
   endPointRepository.save(em);

        if(rm.getMessage().equalsIgnoreCase("successs")){
            String info = Infomation.getMessage("Register Success");
          model.addAttribute("info", info);
     
        }
        if(rm.getMessage().equalsIgnoreCase("email")){
            String info = Infomation.getErrorMessage("Registration Failed");
          model.addAttribute("info", info);

        }
        return "register";

    }


    @GetMapping("/customer")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public String readJson1(Model model){

       // System.out.println("entered"+receipe);
        List<CustomerModel> result=new ArrayList<>();

      ObjectMapper mapper = new ObjectMapper();
      try{

        List<CustomerModel> customer = mapper.readValue(new File(filePath), List.class);
        System.out.println(customer.size());
      
        List<CustomerModel> pojos = mapper.convertValue(
    customer,
    new TypeReference<List<CustomerModel>>() { });
    System.out.println(pojos.size());
     
    long maleCount=pojos.stream().filter(s -> s.getGender().equalsIgnoreCase("male")).count();
    System.out.println("The male count"+maleCount);
    
    
    Map<String, List<CustomerModel>> map = pojos.stream().collect(Collectors.groupingBy(CustomerModel::getCategory));

    for (Map.Entry<String, List<CustomerModel>> entry : map.entrySet()) {
      System.out.println(entry.getKey()+":"+ entry.getValue().size());
  }
    
  Map<String, List<CustomerModel>> map1 = pojos.stream().collect(Collectors.groupingBy(CustomerModel::getCustomer_id));
    
  for (Map.Entry<String, List<CustomerModel>> entry : map1.entrySet()) {
    System.out.println(entry.getKey()+":"+ entry.getValue().size());
}
  
  for(CustomerModel frm:pojos){
       // System.out.println(frm.getCustomer_id());
      }
           
    
    }catch(Exception e){
        e.printStackTrace();

      }
      
       model.addAttribute("result",result);
        model.addAttribute("ratingPage", "ratingPage");
        return "index";
    }



    @GetMapping("/viewCustomer")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public String ViewCustomers(Model model){

       // System.out.println("entered"+receipe);
        List<Gender> result=new ArrayList<>();
        Gender g=new Gender();

      ObjectMapper mapper = new ObjectMapper();
      try{

        List<CustomerModel> customer = mapper.readValue(new File(filePath), List.class);
        System.out.println(customer.size());
      
        List<CustomerModel> pojos = mapper.convertValue(
    customer,
    new TypeReference<List<CustomerModel>>() { });
    System.out.println(pojos.size());
     
    long maleCount=pojos.stream().filter(s -> s.getGender().equalsIgnoreCase("male")).count();
    System.out.println("The male count"+maleCount);
    g.setMale(maleCount);

    long FemaleCount=pojos.stream().filter(s -> s.getGender().equalsIgnoreCase("Female")).count();
    System.out.println("The Female count"+FemaleCount);
    g.setFemale(FemaleCount);
    result.add(g);
    }catch(Exception e){
        e.printStackTrace();

      }
      
       model.addAttribute("result",result);
        model.addAttribute("userspage", "userspage");
        return "Admin";
    }


    @GetMapping("/viewCluster")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public String ViewCluster(Model model){

       // System.out.println("entered"+receipe);
        List<CategoryCluster> result=new ArrayList<>();

      ObjectMapper mapper = new ObjectMapper();
      try{

        List<CustomerModel> customer = mapper.readValue(new File(filePath), List.class);
        System.out.println(customer.size());
      
        List<CustomerModel> pojos = mapper.convertValue(
    customer,
    new TypeReference<List<CustomerModel>>() { });
   
  
    Map<String, List<CustomerModel>> map = pojos.stream().collect(Collectors.groupingBy(CustomerModel::getCategory));

    for (Map.Entry<String, List<CustomerModel>> entry : map.entrySet()) {
    
      CategoryCluster cc=new CategoryCluster();
      cc.setCategoryName(entry.getKey());
      cc.setCount(entry.getValue().size());
      result.add(cc);
  }
   
    
    }catch(Exception e){
        e.printStackTrace();

      }
      
       model.addAttribute("result",result);
        model.addAttribute("clusterView", "clusterView");
        return "Admin";
    }


    @GetMapping("/generateCoupon/{category}")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public String generateCoupon(Model model,@PathVariable String category){

       // System.out.println("entered"+receipe);
        List<CustomerModel> result=new ArrayList<>();
        List<List<CustomerModel>> result1=new ArrayList<>();

      ObjectMapper mapper = new ObjectMapper();
      try{

        List<CustomerModel> customer = mapper.readValue(new File(filePath), List.class);
        System.out.println("customers"+customer.size());
      
        List<CustomerModel> pojos = mapper.convertValue(
    customer,
    new TypeReference<List<CustomerModel>>() { });
   
  
    Map<String, List<CustomerModel>> map = pojos.stream().collect(Collectors.groupingBy(CustomerModel::getCategory));

    for (Map.Entry<String, List<CustomerModel>> entry : map.entrySet()) {
    
      if(entry.getKey().equalsIgnoreCase(category)){


       result1.add(entry.getValue());
    
      }
      
      
  }
   
    
    }catch(Exception e){
        e.printStackTrace();

      }
      
List<CustomerModel> cc=result1.get(0);

for(int i=0;i<=5;i++){
  System.out.println(cc.get(i).toString());
  result.add(cc.get(i));
}
      
    model.addAttribute("result1",result);
      model.addAttribute("coupounView", "coupounView");
        return "Admin";
    }


}
