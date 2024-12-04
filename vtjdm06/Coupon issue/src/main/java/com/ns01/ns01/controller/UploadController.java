package com.ns01.ns01.controller;




import java.io.IOException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ns01.ns01.Repository.EndPointRepository;
import com.ns01.ns01.config.Configuration;
import com.ns01.ns01.model.EndpointsModel;
import com.ns01.ns01.utils.ResponseMessage;
import com.ns01.ns01.utils.ResponseMessageForFile;

@Controller
public class UploadController {

    @Autowired
    EndPointRepository endPointRepository;

    @GetMapping("/upload")
    public String upload(Model model){
   
        model.addAttribute("upload", "upload");
        return "index";
 }

 /**
 * @param userId
 * @param file
 * @return
 * @throws IOException
 */
@PostMapping("/uploadSubmit")
 public String uploadSubmit(@RequestParam("userid") String userId,  @RequestParam("file") MultipartFile file,Model model) throws IOException{

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body= new LinkedMultiValueMap<>(); 
    body.add("file", new ByteArrayResource(file.getBytes())); //MultipartFile 
    body.add("userid", userId);
    body.add("fileName",file.getOriginalFilename());
   
    HttpEntity<?> entity = new HttpEntity<MultiValueMap<String, Object>>(body,headers); 
    EndpointsModel em=new EndpointsModel();
    em.setEndPointName("upload");em.setEndpointUrl(Configuration.getIP()+"upload/fileUpload");em.setStatus(1);
    endPointRepository.save(em);
    ResponseMessage rm=Configuration
    .getRestTemplate()
    .postForObject(Configuration.getIP()+"upload/fileUpload", entity, ResponseMessage.class);
   
    if(rm.getMessage().equalsIgnoreCase("success")){
   model.addAttribute("msg","file Upload Success");
   model.addAttribute("upload", "upload");
    }else{
        model.addAttribute("msg","file failed to upload");
        model.addAttribute("upload", "upload");
    }
     return "index";
}

@GetMapping("/view/{userId}")
public String getFiles(@PathVariable String userId,Model model, HttpSession session){
  
    EndpointsModel em=new EndpointsModel();
    em.setEndPointName("viewfiles");em.setEndpointUrl(Configuration.getIP()+"$$upload/userFiles");em.setStatus(0);
    endPointRepository.save(em);
    ResponseMessageForFile rm=Configuration
    .getRestTemplate()
    .getForObject(Configuration.getIP()+"upload/userFiles/"+userId,ResponseMessageForFile.class);
    if(rm.getFileModel().isEmpty()){
      
        model.addAttribute("msg", "No files found ");
        model.addAttribute("view", "view");
    }else{
        model.addAttribute("files", rm.getFileModel());
        model.addAttribute("view", "view");
    }

    return "index";
}

@GetMapping("/userHarmFiles/{userId}")
public String getHarmFiles(@PathVariable String userId,Model model, HttpSession session){
  
    ResponseMessageForFile rm=Configuration
    .getRestTemplate()
    .getForObject(Configuration.getIP()+"upload/userHarmFiles/"+userId,ResponseMessageForFile.class);
    if(rm.getFileModel().isEmpty()){
      
        model.addAttribute("msg", "No files found ");
        model.addAttribute("harmFiles", "harmFiles");
    }else{
        model.addAttribute("files", rm.getFileModel());
        model.addAttribute("harmFiles", "harmFiles");
    }

    return "index";
}

@GetMapping("/deleteFile/{fileId}")
public String deleteFile(@PathVariable String fileId){

    String s=Configuration
    .getRestTemplate()
    .getForObject(Configuration.getIP()+"upload/deleteFile/"+fileId,String.class);
    System.out.println(s);
    return "index";
}

    
}