package ch.so.agi.search.jsf;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Named
@RequestScoped
public class AutoCompleteView {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    private String txt1;
    private String txt2;
    private String txt3;
    private String txt4;
    private String txt5;
    private String txt6;
    private String txt7;
    private String txt8;
    private Theme theme1;
    private Theme theme2;
    private Theme theme3;
    private Theme theme4;
    private List<Theme> selectedThemes;
     
    @Autowired
    private ObjectMapper objectMapper;

    @Inject
    private ThemeService service;
     
    public List<String> completeText(String query) throws IOException {
        List<String> results = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            results.add(query + i);
        }
        
        logger.info("completeText");
        
        String encodedSearchText = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
        URL url = new URL("https://geo.so.ch/api/search/v2/?filter=foreground,ch.so.agi.gemeindegrenzen,ch.so.agi.av.gebaeudeadressen.gebaeudeeingaenge,ch.so.agi.av.bodenbedeckung,ch.so.agi.av.grundstuecke.projektierte,ch.so.agi.av.grundstuecke.rechtskraeftig,ch.so.agi.av.nomenklatur.flurnamen,ch.so.agi.av.nomenklatur.gelaendenamen&searchtext="+encodedSearchText);
        logger.info(url.toString());
        URLConnection request = url.openConnection();
        request.connect();
        
        JsonNode root = objectMapper.readTree(new InputStreamReader((InputStream) request.getContent()));
        ArrayNode resultArray = (ArrayNode) root.get("results");
        
        Iterator<JsonNode> it = resultArray.iterator();
        while(it.hasNext()) {            
			JsonNode node = it.next();
			logger.info(node.get("feature").get("dataproduct_id").toString());
        }


        
        
//        logger.info("size {}", resultArray.size());
//        logger.info(root.toPrettyString());
//        logger.info("class {} ", root.get("results").getClass());

        return results;
    }
     
    public List<Theme> completeTheme(String query) {
    	
        String queryLowerCase = query.toLowerCase();
        List<Theme> allThemes = service.getThemes();
        return allThemes.stream().filter(t -> t.getName().toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }
     
    public List<Theme> completeThemeContains(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Theme> allThemes = service.getThemes();
        return allThemes.stream().filter(t -> t.getName().toLowerCase().contains(queryLowerCase)).collect(Collectors.toList());
    }
         
    public void onItemSelect(SelectEvent event) {
    	logger.info("onItemSelect");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item Selected", event.getObject().toString()));
    }
 
    public String getTxt1() {
        return txt1;
    }
 
    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }
 
    public String getTxt2() {
        return txt2;
    }
 
    public void setTxt2(String txt2) {
        this.txt2 = txt2;
    }
 
    public String getTxt3() {
        return txt3;
    }
 
    public void setTxt3(String txt3) {
        this.txt3 = txt3;
    }
 
    public String getTxt4() {
        return txt4;
    }
 
    public void setTxt4(String txt4) {
        this.txt4 = txt4;
    }
 
    public String getTxt5() {
        return txt5;
    }
 
    public void setTxt5(String txt5) {
        this.txt5 = txt5;
    }
 
    public String getTxt6() {
        return txt6;
    }
 
    public void setTxt6(String txt6) {
        this.txt6 = txt6;
    }
 
    public String getTxt7() {
        return txt7;
    }
 
    public void setTxt7(String txt7) {
        this.txt7 = txt7;
    }
 
    public String getTxt8() {
        return txt8;
    }
 
    public void setTxt8(String txt8) {
        this.txt8 = txt8;
    }
 
    public Theme getTheme1() {
        return theme1;
    }
 
    public void setTheme1(Theme theme1) {
        this.theme1 = theme1;
    }
 
    public Theme getTheme2() {
        return theme2;
    }
 
    public void setTheme2(Theme theme2) {
        this.theme2 = theme2;
    }
 
    public Theme getTheme3() {
        return theme3;
    }
 
    public void setTheme3(Theme theme3) {
        this.theme3 = theme3;
    }
 
    public Theme getTheme4() {
        return theme4;
    }
 
    public void setTheme4(Theme theme4) {
        this.theme4 = theme4;
    }
 
    public List<Theme> getSelectedThemes() {
        return selectedThemes;
    }
 
    public void setSelectedThemes(List<Theme> selectedThemes) {
        this.selectedThemes = selectedThemes;
    }
     
    public void setService(ThemeService service) {
        this.service = service;
    }
     
    public char getThemeGroup(Theme theme) {
        return theme.getDisplayName().charAt(0);
    }
}