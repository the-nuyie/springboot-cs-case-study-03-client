package com.training.java.springboot.casestudy.controller;

import com.training.java.springboot.casestudy.bean.Student;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// @RestController
@Controller
@RequestMapping("/controller/jsp")
public class JspController {

    Logger logger = LogManager.getLogger(JspController.class);

    // For call REST Web Service Server (Back End)
    @Autowired
    private RestTemplate restTemplate;

    @Value("${backend.main.url}")
    private String BACKEND_MAIN_URL;
    @Value("${backend.rest.url}")
    private String BACKEND_REST_URL;

    @GetMapping("/home")
    public String home(Model model) {
        String destinationJsp = "home";
        return destinationJsp;
    }

    // @PostMapping("/viewStudents")
    @GetMapping("/view-students")
    public String viewStudents(Model model) {

        String destinationJsp = null;
        String errorMessage = null;

        logger.debug("ViewStudent START");

        try{
            List<Student> listStudent = null;

            // Method 1 : Create fake list
            // listStudent = new ArrayList<>();
            // listDummyStudent(listStudent);

            // Method 2 : Call Back End
            String uri = BACKEND_REST_URL+"/list-students";

            /*
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<Map<String, Object>>(){});
            Map<String, Object> result = response.getBody();
            */
            ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);
            Map<String, Object> result = response.getBody();

            logger.debug("Raw result = "+result);
            if(result != null){
                String code = (String)result.get("CODE");
                String message = (String)result.get("MESSAGE");

                if(code != null && code.equals("0000")){
                    listStudent = new ArrayList<>();
                    List<Map> listMap = (List<Map>)result.get("RESULT");
                    logger.debug("  List student size = "+listMap.size());
                    for(int i=0; i<listMap.size(); i++){
                        Map m = listMap.get(i);
                        Student st = convertMapToStudent(m);
                        listStudent.add(st);
                        logger.debug("    List("+(i+1)+") student = "+st);
                    }
                }
            }



            // logger.debug("ListStudent = "+listStudent);
            model.addAttribute("students", listStudent);
            destinationJsp = "view-students";

        }catch(Exception ex){
            ex.printStackTrace();
            errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            destinationJsp = "error";
        }

        logger.debug("ViewStudent END");

        return destinationJsp;
    }

    @GetMapping("/input-student")
    public String inputStudent(Model model) {
        String destinationJsp = "input-student";
        return destinationJsp;
    }

    @PostMapping("/add-student")
    public ModelAndView addStudent(@RequestParam("firstname") String firstname,
                                   @RequestParam("lastname") String lastname,
                                   @RequestParam("room") String room,
                                   ModelMap model) {

        String destinationJsp = "";
        logger.debug("addStudent() START");
        logger.debug("  parameters : firstname={}, lastname={}, room={}",firstname,lastname,room);

        Student student = new Student();
        student.setFirstname(firstname);
        student.setLastname(lastname);
        student.setRoom(room);

        logger.debug("addStudent() END");

        try{
            List<Student> listStudent = null;

            // Method 1 : fake data
            // listStudent = new ArrayList<>();
            // listStudent.add(student);

            // Method 2 : Call Back End
            // Method 2 : Call Back End
            String uri = BACKEND_REST_URL+"/add-student";
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<Student> entity = new HttpEntity<Student>(student,headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    uri, HttpMethod.POST, entity,
                    new ParameterizedTypeReference<Map<String, Object>>(){});
            Map<String, Object> result = response.getBody();


            logger.debug("Raw result = "+result);
            if(result != null){
                String code = (String)result.get("CODE");
                String message = (String)result.get("MESSAGE");
                logger.debug("  REST CODE = "+code);
            }

            return new ModelAndView("redirect:./view-students", model);
        }catch(Exception ex){
            ex.printStackTrace();
            model.addAttribute("errorMessage", ex.getMessage());
            return new ModelAndView("error", model);
        }

    }

    private void listDummyStudent(List<Student> listStudent){

        Student st1 = new Student();
        st1.setFirstname("Bukayo");
        st1.setLastname("Saka");
        st1.setId(99001);
        st1.setRoom("A1");
        st1.setUpdatedDate(new Date());

        Student st2 = new Student();
        st2.setFirstname("William");
        st2.setLastname("Saliba");
        st2.setId(99002);
        st2.setRoom("A1");
        st2.setUpdatedDate(new Date());

        listStudent.add(st1);
        listStudent.add(st2);
    }

    private Student convertMapToStudent(Map m){
        Student st = new Student();

        st.setFirstname((String)m.get("firstname"));
        st.setLastname((String)m.get("lastname"));
        st.setId((Integer)m.get("id"));
        st.setRoom((String)m.get("room"));


        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Date date = formatter.parse(testDate);
        if(m.get("updatedDate") != null){
            try {
                st.setUpdatedDate(formatter.parse( (String)m.get("updatedDate") ));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        if(m.get("createdDate") != null){
            try {
                st.setCreatedDate( formatter.parse( (String)m.get("createdDate") ) );
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }


        return st;
    }
}
