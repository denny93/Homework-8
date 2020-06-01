package Main;

import java.io.ObjectInput;
import java.util.ArrayList;
import java.util.Scanner;


   public class Database {

       static ArrayList<String[]> arrayBase = new ArrayList<String[]>();
       static ArrayList<String[]> arrayKids = new ArrayList<String[]>();
       static String[] parameters_base = new String[9];
       static String[] parameters_types = new String[3];
       static String[] parameters_kidas = new String[4];

       public static void main(String[] args) {
           parameters_base[0] = "FNAME";
           parameters_base[1] = "LNAME";
           parameters_base[2] = "AGE";
           parameters_base[3] = "ADDRESS";
           parameters_base[4] = "PHONE";
           parameters_base[5] = "SEX";
           parameters_base[6] = "TYPE";
           parameters_base[7] = "TYPE_PAYMENT";
           parameters_base[8] = "VALUE";


           parameters_types[0] = "R";
           parameters_types[1] = "W";
           parameters_types[2] = "M";

           parameters_kidas[0] = "KFNAME";
           parameters_kidas[1] = "KLNAME";
           parameters_kidas[2] = "KAGE";
           parameters_kidas[3] = "GRAJDANIN";

           EnterInformation();
           SelectStatment();
       }

       private static void SelectStatment() {
           Scanner scan = new Scanner(System.in);
           String enterInformation = scan.next();

           if (enterInformation.contains("EXIT")) {
               return;
           } else {
               if (enterInformation.contains("SELECT")) {
                   String input = enterInformation.replace("SELECT", "");
                   String[] bace = input.split("::");
                   String[] base_parameters = bace[0].replace("@{SPECIAL_PROPERTY}", "").split("@");
                   String[] model = new String[9];
                   ArrayList<String[]> result = new ArrayList<String[]>();
                   int first = 0;
                   for (int i = 0; i < base_parameters.length; i++) {
                       int index = 0;
                       String seletor = base_parameters[i].substring(base_parameters[i]
                               .indexOf("{") + 1, base_parameters[i].indexOf("}"));
                       for (int j = 0; j < parameters_base.length; j++) {
                           if (parameters_base[j].contains(seletor)) {
                               index = j;
                               break;
                           }
                       }

                       for (int k = 0; k < arrayBase.size(); k++) {
                           if (base_parameters[i].contains("SPECIAL_PROPERTY}")) {
                               continue;
                           }
                           String values = base_parameters[i].substring(base_parameters[i]
                                   .indexOf("[") + 1, base_parameters[i].indexOf("]"));
                           if (first == 0) {
                               if (arrayBase.get(k)[index].contains(values)) {
                                   result.add(arrayBase.get(k));
                                   first++;
                               }
                           } else {
                               if (result.get(k)[index].contains(values)) {

                               } else {
                                   result = new ArrayList<String[]>();
                               }
                           }
                       }

                   }
                   // proverka za dete
                   if (input.contains("KID}=")) {
                       String[] model_kid = new String[7];
                       String[] bace1 = input.split("::");
                       String[] kid_bace = bace1[1].split("KID}=");
                       for (int i = 1; i < kid_bace.length; i++) {
                           String[] info = kid_bace[i].split("@");
                           for (int j = 0; j < info.length; j++) {
                               String information = "";
                               int index = 0;
                               String selector = info[j]
                                       .substring(info[j].indexOf("{") + 1, info[j].indexOf("}"));
                               for (int k = 0; k < parameters_kidas.length; k++) {
                                   if (parameters_kidas[j].contains(selector)) {
                                       index = k;
                                       break;
                                   }
                               }

                               for (int k = 0; k < arrayKids.size(); k++) {
                                   if (info[k].contains("]]")) {
                                       String data = info[k].substring(info[k].indexOf("=[") + 2, info[k].indexOf("]]"));
                                       if (arrayKids.get(k)[index].contains(data)) {
                                           for (int m = 0; m < arrayBase.size(); m++) {
                                               if (m == Integer.parseInt(arrayKids.get(m)[3])) {
                                                   result.add(arrayBase.get(m));
                                                   break;
                                               }
                                           }
                                       }
                                   } else {
                                       model_kid[index] = info[j].substring(info[j].indexOf("=[") + 1, info[j].indexOf("]")).replace("[", "");
                                   }

                               }
                           }
                       }

                   }
                   if (result.size() == 0) {
                       System.out.println("No result");
                       return;
                   }
                   for (int i = 0; i < result.size(); i++) {
                       System.out.println(result.get(i)[0] + " | " + result.get(i)[1] + " | "
                               + result.get(i)[2] + " | " + result.get(i)[3] + " | "
                               + result.get(i)[4] + " | " + result.get(i)[5] + " | " +
                               result.get(i)[6] + " | " + result.get(i)[7] + " | " +
                               result.get(i)[8] + " | ");

                   }

                   SelectStatment();
               } else {
                   System.out.println("Please enter select statment in your query!");
                   SelectStatment();
               }
           }
       }

       private static void EnterInformation() {
           try {
               Scanner scan = new Scanner(System.in);
               String input = scan.next();

               if (input.contains("EXIT")) {
                   return;
               } else {
                   String[] bace = input.split("::");
                   String[] base_parameters = bace[0].replace("@{SPECIAL_PROPERTY}", "").split("@");
                   String[] model = new String[9];
                   for (int i = 0; i < base_parameters.length; i++) {
                       int index = 0;
                       String seletor = base_parameters[i].substring(base_parameters[i].indexOf("{") + 1, base_parameters[i].indexOf("}"));
                       for (int j = 0; j < parameters_base.length; j++) {
                           if (parameters_base[j].contains(seletor)) {
                               index = j;
                               break;
                           }
                       }
                       model[index] = base_parameters[i].substring(base_parameters[i].indexOf("[") + 1, base_parameters[i].indexOf("]"));
                   }

                   if (bace[1].contains("PENSION")) {
                       model[7] = "PENSION";
                       model[8] = bace[1].substring(bace[1].indexOf("[") + 1, bace[1].indexOf("]"));
                   } else if (bace[1].contains("SALARY")) {
                       model[7] = "SALARY";
                       model[8] = bace[1].substring(bace[1].indexOf("[") + 1, bace[1].indexOf("]"));
                   }
                   arrayBase.add(model);


                   // ako ima deca
                   if (bace[1].contains("KID")) {
                       String[] model_kid = new String[7];
                       String[] kid_bace = bace[1].split("KID}=");
                       for (int i = 1; i < kid_bace.length; i++) {

                           String[] info = kid_bace[i].split("@");
                           for (int j = 0; j < info.length; j++) {
                               String information = "";
                               int index = 0;
                               String seletor = info[j].substring(info[j].indexOf("{") + 1, info[j].indexOf("}") - 1);
                               for (int k = 0; k < parameters_kidas.length; k++) {
                                   if (parameters_kidas[k].contains(seletor)) {
                                       index = k;
                                       break;
                                   }
                               }

                               if (info[j].contains("]]")) {
                                   model_kid[index] = info[j].substring(info[j].indexOf("=[") + 1, info[j].indexOf("]]"));
                               } else {
                                   model_kid[index] = info[j]
                                           .substring(info[j].indexOf("=[") + 1, info[j].indexOf("]"))
                                           .replace("[", "");
                               }
                           }

                           model_kid[3] = String.valueOf(arrayBase.size() - 1);
                           arrayKids.add(model_kid);

                       }
                   }
               }
           } catch (Exception ex) {
               System.out.println("Please enter correct information");
               EnterInformation();
           }
       }
   }
