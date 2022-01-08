package com.modeling.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class SmoController {
    @GetMapping("/li")
    public String li(@RequestParam(defaultValue = "1") String li,
                      Model model) {

        model.addAttribute("li",li);
        return "one";
    }



    @GetMapping("/one")
    public String one(@RequestParam(defaultValue = "null") String lambda,
                           @RequestParam(defaultValue = "null") String time,
                           Model model) {
        float l = 0f;
        float t = 0f;
        try {
             l = Float.parseFloat(lambda);
             t = Float.parseFloat(time);
        }
        catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }

        if (!Float.isNaN(l) && !Float.isNaN(t)) {
            QueueOne queueOne = new QueueOne();
            float[] arrayRequest = queueOne.oneChanelQWithDeclines1(l, t);
            model.addAttribute("li","1");
            model.addAttribute("oneChanel", arrayRequest);
        }

        return "one";
    }

    @GetMapping("/two")
    public String two(@RequestParam(defaultValue = "null") String lambda,
                           @RequestParam(defaultValue = "null") String time,
                           @RequestParam(defaultValue = "null") String kol,
                           Model model) {
        float l = 0f;
        float t = 0f;
        int c = 0;
        try {
            l = Float.parseFloat(lambda);
            t = Float.parseFloat(time);
            c = Integer.parseInt(kol);
        }
        catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }

        if (!Float.isNaN(l) && !Float.isNaN(t) && !Float.isNaN(c)) {
            QueueOne queueOne = new QueueOne();
            float[] arrayRequest = queueOne.mulChanelQWithDeclines(c,l,t);
            model.addAttribute("li","2");
            model.addAttribute("twoChanel", arrayRequest);
        }

        return "one";
    }
    @GetMapping("three")
    public String three(@RequestParam(defaultValue = "null") String lambda,
                      @RequestParam(defaultValue = "null") String time,
                        @RequestParam(defaultValue = "null") String qantitybayers,
                      Model model) {
        float l = 0f;
        float t = 0f;
        int qb = 0;
        try {
            l = Float.parseFloat(lambda);
            t = Float.parseFloat(time);
            qb = Integer.parseInt(qantitybayers);
        }
        catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }

        if (!Float.isNaN(l) && !Float.isNaN(t)) {
            QueueOne queueOne = new QueueOne();
            float[] arrayRequest = queueOne.oneChanelQ(l, t, qb);
            model.addAttribute("n",qb);
            model.addAttribute("li","3");
            model.addAttribute("threeChanel", arrayRequest);
        }

        return "one";
    }

    @GetMapping("/four")
    public String four(@RequestParam(defaultValue = "null") String lambda,
                      @RequestParam(defaultValue = "null") String time,
                      @RequestParam(defaultValue = "null") String kol,
                      Model model) {
        float l = 0f;
        float t = 0f;
        int c = 0;
        try {
            l = Float.parseFloat(lambda);
            t = Float.parseFloat(time);
            c = Integer.parseInt(kol);
        }
        catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }

        if (!Float.isNaN(l) && !Float.isNaN(t) && !Float.isNaN(c)) {
            QueueOne queueOne = new QueueOne();
            float[] arrayRequest = queueOne.mulChanelQ(c,l,t);
            model.addAttribute("li","4");
            model.addAttribute("fourChanel", arrayRequest);
        }

        return "one";
    }

    @GetMapping("/five")
    public String five(@RequestParam(defaultValue = "null") String lambda,
                       @RequestParam(defaultValue = "null") String time,
                       @RequestParam(defaultValue = "null") String kol,
                       @RequestParam(defaultValue = "null") String Tall,
                       Model model) {
        float l = 0f;
        float t = 0f;
        float c = 0;
        float T = 0;
        try {
            l = Float.parseFloat(lambda);
            t = Float.parseFloat(time);
            c = Float.parseFloat(kol);
            T = Integer.parseInt(Tall);

        }
        catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }

        if (!Float.isNaN(l) && !Float.isNaN(t) && !Float.isNaN(c)) {
            QueueOne queueOne = new QueueOne();
            float[] arrayRequest = queueOne.oneChanelQWithBorder(l,t,c);
            float[] pCount = queueOne.modeling(T);
            float[] Tp = queueOne.getTp(pCount,T);
            model.addAttribute("Tp", Tp);
            model.addAttribute("modelCount", pCount);
            model.addAttribute("k",c);
            model.addAttribute("li","5");
            model.addAttribute("fiveChanel", arrayRequest);
        }

        return "one";
    }

    public static class QueueOne {

        public QueueOne() {
        }

        public float[] oneChanelQWithDeclines1(float lambd, float u) {
            float t_service = u / (60.0F);
            float intensity_service = (1.0F) / t_service;
            float intensity_var = lambd / intensity_service;
            float p0 = (1.0F) / ((intensity_var + (1.0F)));
            float p1 = intensity_var * p0;
            float decline_prob = p1;
            float Q = (1.0F) - decline_prob;
            float A = lambd * Q;
            float[] answer = new float[] {A, Q, p0, p1};
            return answer;
        }

        public float[] mulChanelQWithDeclines(int c, float lambd, float u) {
            float intensive = u;
            float time = lambd;
            int n = c + 1;;
            float intensivem = (1.0F) / (time/ (60.0F));
            float[] p = new float[n];
            float sum = 1.0F;
            p[0] = 1.0F;
            for (int i = 1; i < n; i++) {
                p[i] += (((intensive / ((intensivem * ((float)(i)))))) * p[i - 1]);
                sum += p[i];
            }
            p[0] = (1.0F) / sum;
            for (int i = 1; i < n; i++) {
                p[i] = ((intensive / ((intensivem * ((float)(i)))))) * p[i - 1];
            }
            float Q = (1.0F) - p[n - 1];
            float A = Q * time;
            float no = 0.0F;
            for (int i = 0; i < n; i++) {
                no += (((float)((n - i - 1))) * p[i]);
            }
            float K = no / ((float)((n - 1)));
            float nz = ((intensive / intensivem)) * Q;
            float kz = nz / ((float)((n - 1)));
            float[] answer = new float[] {A, Q, no, K, nz, kz};
//            float[] final_answer = new float[n + 6];
            //            Arrays.cop.CopyTo(final_answer, 0) /* error */;
            float[] final_answer = new float[p.length+answer.length];
            int i=0;
            for (;i<p.length;++i){
                final_answer[i] = p[i];
            }
            for (int j =0; j<answer.length;++j){
                final_answer[i] = answer[j];
                i++;
            }
            System.out.println(final_answer.length);
            return final_answer;
        }

        public float[] oneChanelQ(float lambd, float u, int quantitybayer) {
            float ro = lambd / u;
            float p0 = (1.0F) - ro;
            float p4 = (float)((Math.pow((double)ro, 4.0) * ((double)(((1.0F) - ro)))));
            float L = (float)((Math.pow((double)ro, 2.0) / ((double)(((1.0F) - ro)))));
            float T = L / lambd;
            float lsys = ro / (((1.0F) - ro));
            float tsys = lsys / lambd;
            float quantitybuyers = (float) (Math.pow(ro,quantitybayer) * (1f-ro));
            float[] answer = new float[] {p0, (1.0F) - p0, L, T*60f, lsys, tsys*60f, quantitybuyers};
            return answer;
        }

        public float[] mulChanelQ(int c, float lambd, float u) {
            float intensive = lambd;
            float intensivem = u;
            int n = c + 1;
            float po = 1.0F;
            int s = 1;
            for (int i = 1; i < n; i++) {
                s *= i;
                po += (((float)(Math.pow((double)((intensive / intensivem)), (double)i))) / ((float)s));
            }
            po += (((float)(Math.pow((double)((intensive / intensivem)), (double)n))) / ((((float)s) * ((((float)n) - (intensive / intensivem) - (1.0F))))));
            po = (1.0F) / po;
            float lo = (float)(((((Math.pow((double)((intensive / intensivem)), (double)n)) * ((double)po))) / ((((double)s) * ((double)((n - 1))) * (Math.pow((double)((1.0F) - ((((intensive / intensivem)) / ((float)((n - 1)))))), 2.0))))));
            float to = ((lo / intensive)) * (60.0F);
            float lc = (intensive / intensivem) + lo;
            float tc = ((lc / intensive)) * (60.0F);
            float[] answer = new float[] {lo, to, lc, tc};
            return answer;
        }

        public float[] getTp(float[] pcount, float T){
            float[] a = new float[pcount.length];
            for(int i = 0; i<pcount.length; i++){
                a[i] = pcount[i]/T;
            }
            pcount = null;
            return a;
        }

        public float random(){
            return new Random().nextFloat();
        }
        static public int p0count;
        static public int p1count;
        static public int p2count;
        static public int p_otkcount;
        public float[] modeling(float T) {
            QueueOne.p0count = 0;
            QueueOne.p1count = 0;
            QueueOne.p2count = 0;
            QueueOne.p_otkcount = 0;

            for (int i = 0; i < T; i++) {
                float a = random();
                float[] b = new float[4];
                b[0] = p0;
                b[1] = p1;
                b[2] = p2;
                b[3] = p_otk;
                Arrays.sort(b);
                float prev = b[0];
                for (int j = 0; j < b.length; ++j) {
                    if (a >= b[j]) {
                        prev = b[j];
                    } else break;
                }
                System.out.println(prev);
                if (prev == p0) {
                    System.out.println("Выпало событие p0");
                    p0count++;
                } else if (prev == p1) {
                    System.out.println("Выпало событие p1");
                    p1count++;
                } else if (prev == p2) {
                    System.out.println("Выпало событие p2");
                    p2count++;
                } else {
                    System.out.println("Выпало событие p отк = " + p_otk);
                    p_otkcount++;
                }
            }

            System.out.println("Количество состояний p0 = " + QueueOne.p0count + " Количество состояний p1 = " + QueueOne.p1count
                    + " Количество состояний p2 = " + QueueOne.p2count + " Количество состояний pотк = " + QueueOne.p_otkcount);
            System.out.println("Количество состояний p0 = " + ((double) QueueOne.p0count / (double) T)
                    + " Количество состояний p1 = " + ((double) QueueOne.p1count / (double) T)
                    + " Количество состояний p2 = " + ((double) QueueOne.p2count / (double) T) + " Количество состояний pотк = "
                    + ((double) QueueOne.p_otkcount / (double) T));
            return new float[]{p0count,p1count,p2count,p_otkcount};
        }
        public static  float p0;
        public static float p1;
        public static float p2;
        public static float p_otk;
        public float[] oneChanelQWithBorder(float lambd, float u, float m) {
            float ro = lambd / u;
            p0 = (float)((((double)(((1.0F) - ro))) / (((1.0) - Math.pow((double)ro, (double)(m + (2.0F)))))));
            p1 = ro * p0;
            p2 = ro * p1;
            p_otk = ro * p2;
            float Q = (1.0F) - p_otk;
            float A = lambd * Q;
            float L = (float)((Math.pow((double)ro, 2.0) * (((((1.0) - (Math.pow((double)ro, (double)m) * ((double)(((m + (1.0F)) - ((m * ro)))))))) / (((((1.0) - Math.pow((double)ro, (double)(m + (2.0F))))) * ((double)(((1.0F) - ro)))))))));
            float T = ((1.0F) / lambd) * L;
            float l_obs = (1.0F) - p0;
            float lsys = L + l_obs;
            float tsys = ((1.0F) / lambd) * lsys;
            float[] answer = new float[] {Q, A, p_otk, L, T*60, lsys, tsys* 60f};
            return answer;
        }
    }

}
