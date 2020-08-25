package net.guides.springboot2.crud.controller;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")


public class CaptchaController {

    HttpSession CaptchaSession;

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletResponse response,HttpServletRequest request) throws IOException {

        response.setContentType("image/jpg");
        int iTotalChars = 7;
        int iHeight = 40;
        int iWidth = 170;
        Font fntStyle1 = new Font("Arial", Font.BOLD, 30);
        Random randChars = new Random();
        String sImageCode = (Long.toString(Math.abs(randChars.nextLong()), 36)).substring(0, iTotalChars);
        BufferedImage biImage = new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2dImage = (Graphics2D) biImage.getGraphics();
        int iCircle = 15;
        for (int i = 0; i < iCircle; i++) {
            g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
        }
        g2dImage.setFont(fntStyle1);
        for (int i = 0; i < iTotalChars; i++) {
            g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
            if (i % 2 == 0) {
                g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 24);
            } else {
                g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 35);
            }
        }
//        OutputStream osImage = response.getOutputStream();
        ByteArrayOutputStream osImage = new ByteArrayOutputStream();
        ImageIO.write(biImage, "PNG", osImage);

        byte[] bytes = osImage.toByteArray();

        String base64bytes = Base64.encode(bytes);
        String src = "data:image/png;base64," + base64bytes;

        CaptchaSession = request.getSession();
        CaptchaSession.setAttribute("captcha_img",sImageCode);
        return src;

    }
    @CrossOrigin
    @RequestMapping(value = "save", method = RequestMethod.POST)
        public int save(@RequestBody Map<String, String> payload) {
        String captcha = CaptchaSession.getAttribute("captcha_img").toString();

        if (captcha.equals(payload.get("captcha"))){

        return 1;
        }
        else{
            return 0;
        }
    }
}