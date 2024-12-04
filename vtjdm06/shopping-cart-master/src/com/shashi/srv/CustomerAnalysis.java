/*
 * package com.shashi.srv;
 * 
 * import java.io.IOException;
 * 
 * import javax.servlet.ServletException; import javax.servlet.http.HttpServlet;
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * public class CustomerAnalysis extends HttpServlet {
 * 
 * 
 * @Override protected void doPost(HttpServletRequest req, HttpServletResponse
 * resp) throws ServletException, IOException {
 * 
 * 
 * @GetMapping("/readJson1")
 * 
 * @JsonIgnoreProperties(ignoreUnknown = true) public String readJson(Model
 * model,@RequestParam("receipe") String receipe){
 * 
 * System.out.println("entered"+receipe); List<FoodReceipeModel> result=new
 * ArrayList<>();
 * 
 * ObjectMapper mapper = new ObjectMapper(); try{
 * 
 * List<FoodReceipeModel> customer = mapper.readValue(new
 * File("C:/Users/shyam/Downloads/recipes.json"), List.class);
 * System.out.println(customer.size());
 * 
 * List<FoodReceipeModel> pojos = mapper.convertValue( customer, new
 * TypeReference<List<FoodReceipeModel>>() { });
 * System.out.println(pojos.size()); for(FoodReceipeModel frm:pojos){
 * if(frm.getName().contains(receipe)){ System.out.println(frm);
 * 
 * List<String> str=frm.getIngredients();
 * System.out.println("ingredddd"+str.get(0));
 * 
 * result.add(frm); } }
 * 
 * 
 * }catch(Exception e){ e.printStackTrace();
 * 
 * }
 * 
 * model.addAttribute("result",result); model.addAttribute("ratingPage",
 * "ratingPage"); return "index"; } }
 * 
 * @Override protected void doGet(HttpServletRequest req, HttpServletResponse
 * resp) throws ServletException, IOException {
 * 
 * 
 * 
 * 
 * }
 * 
 * }
 */