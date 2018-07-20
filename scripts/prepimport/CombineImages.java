package prepimport;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import annotations.BasicCSVUtils;
import annotations.CSVTools;


public class CombineImages {

    // original image name to renamed image name
    static Map<String, String> mapping = new HashMap<String, String>();
    
    public CombineImages() {
    }
    
    static void addMapping(String from, String to) {
        String s1 = from.substring(from.indexOf('/') + 1);
        if (mapping.containsKey(s1)) {
            System.err.println("Warning: Duplicate original image file name "+s1);
        }
        mapping.put(s1, to);
    }
    
    public static List<String> Figure01A(List<String> files) {
        // Embryo10.EGFP.Cam_Right_00041.tif
        // Embryo10_C0_T00041.tif
        
        String dir = "Figure01A";
        
        List<String> cmds = new ArrayList<String>();
        
        HashMap<String, Integer> min = new HashMap<String, Integer>();
        HashMap<String, Integer> max = new HashMap<String, Integer>();
        
        for (String file : files) {
            if (!file.startsWith("./"+dir+"/"))
                continue;
            
            String imgName = file.substring(file.lastIndexOf('/')+1);
            String[] name = imgName.split("\\.|_");
            // [Embryo10, EGFP, Cam, Right, 00041, tif]
            
            String basename = name[0];
            
            int channel = 0;
            if (name[1].equals("mCherry"))
                channel = 1;
            
            String rename = basename+"_C"+channel+"_T"+name[4]+".tif";
            
            int x = Integer.parseInt(name[4]);
            int low = min.containsKey(basename) ? min.get(basename) : x;
            if (x <= low) {
                low = x;
                min.put(basename, low);
            }
            int high = max.containsKey(basename) ? max.get(basename) : x;
            if (x >= high) {
                high = x;
                max.put(basename, high);
            }
            
            cmds.add("mv "+file+" ./"+dir+"/"+rename);
            addMapping(file, basename+".pattern");
        }
        
        NumberFormat nf = new DecimalFormat("00000");
        for(String basename : min.keySet()) {
            String pattern = basename+"_C<0-1>_T<"+nf.format(min.get(basename))+"-"+nf.format(max.get(basename))+">.tif";
            cmds.add("echo \""+pattern+"\" > ./"+dir+"/"+basename+".pattern");
        }
        
        return cmds;
    }
    
    public static List<String> Figure01C(List<String> files) {
        // Metaphase1.lif
        // Metaphase_T1.lif
        
        String dir = "Figure01C";
        
        List<String> cmds = new ArrayList<String>();
        
        HashMap<String, Integer> max = new HashMap<String, Integer>();
        
        for (String file : files) {
            if (!file.startsWith("./"+dir+"/"))
                continue;
            
            String imgName = file.substring(file.lastIndexOf('/')+1);
            
            int i = imgName.indexOf('.');
            
            String basename = imgName.substring(0, i-1);
            int t = Integer.parseInt(""+imgName.charAt(i-1));

            String rename = basename+"_T"+t+".lif";

            int high = max.containsKey(basename) ? max.get(basename) : t;
            if (t >= high) {
                high = t;
                max.put(basename, t);
            }
            
            cmds.add("mv "+file+" ./"+dir+"/"+rename);
            addMapping(file, basename+".pattern");
        }
        
        for(String basename : max.keySet()) {
            String pattern = basename+"_T<1-"+max.get(basename)+">.tif";
            cmds.add("echo \""+pattern+"\" > ./"+dir+"/"+basename+".pattern");
        }
        
        return cmds;
    }
    
    public static List<String> Figure02(List<String> files) {
        // Embryo07.EGFP.Cam_Right_00056.tif
        // Embryo07_C0_Z00056.tif
        
        String dir = "Figure02";
        
        List<String> cmds = new ArrayList<String>();
        
        HashMap<String, Integer> min = new HashMap<String, Integer>();
        HashMap<String, Integer> max = new HashMap<String, Integer>();
        
        for (String file : files) {
            if (!file.startsWith("./"+dir+"/"))
                continue;
            
            String imgName = file.substring(file.lastIndexOf('/')+1);
            String[] name = imgName.split("\\.|_");
            // [Embryo07, EGFP, Cam, Right, 00056, tif]
            
            String basename = name[0];
            
            int channel = 0;
            if (name[1].equals("iRFP"))
                channel = 1;
            else if (name[1].equals("mCherry"))
                channel = 2;
            
            String rename = basename+"_C"+channel+"_T"+name[4]+".tif";
            
            int x = Integer.parseInt(name[4]);
            int low = min.containsKey(basename) ? min.get(basename) : x;
            if (x <= low) {
                low = x;
                min.put(basename, low);
            }
            int high = max.containsKey(basename) ? max.get(basename) : x;
            if (x >= high) {
                high = x;
                max.put(basename, high);
            }
            
            cmds.add("mv "+file+" ./"+dir+"/"+rename);
            addMapping(file, basename+".pattern");
        }
        
        NumberFormat nf = new DecimalFormat("00000");
        for(String basename : min.keySet()) {
            String pattern = basename+"_C<0-2>_T<"+nf.format(min.get(basename))+"-"+nf.format(max.get(basename))+">.tif";
            cmds.add("echo \""+pattern+"\" > ./"+dir+"/"+basename+".pattern");
        }
        
        return cmds;
    }
    
    public static List<String> Figure03(List<String> files) {
        // Embryo06.Position_0.stack_0.EGFP.Cam_Right_00064.tif
        // Embryo06_C0_00064.tif
        
        String dir = "Figure03";
        
        List<String> cmds = new ArrayList<String>();
        
        HashMap<String, Integer> min = new HashMap<String, Integer>();
        HashMap<String, Integer> max = new HashMap<String, Integer>();
        
        for (String file : files) {
            if (!file.startsWith("./"+dir+"/"))
                continue;
            
            String imgName = file.substring(file.lastIndexOf('/')+1);
            String[] name = imgName.split("\\.|_");
            // [Embryo01, Position, 0, stack, 0, EGFP, Cam, Right, 00000, tif]
            
            String basename = name[0];
            
            int channel = 0;
            if (name[5].equals("mCherry"))
                channel = 1;
            
            String rename = basename+"_C"+channel+"_T"+name[8]+".tif";
            
            int x = Integer.parseInt(name[8]);
            int low = min.containsKey(basename) ? min.get(basename) : x;
            if (x <= low) {
                low = x;
                min.put(basename, low);
            }
            int high = max.containsKey(basename) ? max.get(basename) : x;
            if (x >= high) {
                high = x;
                max.put(basename, high);
            }
            
            cmds.add("mv "+file+" ./"+dir+"/"+rename);
            addMapping(file, basename+".pattern");
        }
        
        NumberFormat nf = new DecimalFormat("00000");
        for(String basename : min.keySet()) {
            String pattern = basename+"_C<0-1>_T<"+nf.format(min.get(basename))+"-"+nf.format(max.get(basename))+">.tif";
            cmds.add("echo \""+pattern+"\" > ./"+dir+"/"+basename+".pattern");
        }
        
        return cmds;
    }
    
    public static List<String> Figure04(List<String> files) {
        // NocMo.Cell09.Position_0.stack_0.iRFP.Cam_Right_00019.tif 
        // NocMo.Cell09_C0_T00019.tif
        
        String dir = "Figure04";
        
        List<String> cmds = new ArrayList<String>();
        
        HashSet<String> names = new HashSet<String>();
        
        for (String file : files) {
            if (!file.startsWith("./"+dir+"/"))
                continue;
            
            String imgName = file.substring(file.lastIndexOf('/')+1);
            String[] name = imgName.split("\\.");
            // [MoNoc, Cell01, Position_0, stack_0, EGFP, Cam_Right_00008, tif]
            
            String basename = name[0]+"."+name[1];
            names.add(basename);
            
            int channel = 0;
            if (name[4].equals("iRFP"))
                channel = 1;
            
            String rename = basename+"_C"+channel+".tif";
            cmds.add("mv "+file+" ./"+dir+"/"+rename);
            addMapping(file, basename+".pattern");
        }
        
        for(String basename : names) {
            String pattern = basename+"_C<0-1>.tif";
            cmds.add("echo \""+pattern+"\" > ./"+dir+"/"+basename+".pattern");
        }
        
        return cmds;
    }
    
    public static List<String> SupplementaryFigure01(List<String> files) {
        // Embryo13.iRFP.000057_00.tif
        // Embryo13_C0_Z000057.tif
        
        String dir = "SupplementaryFigure01";
        
        List<String> cmds = new ArrayList<String>();
        
        HashMap<String, Integer> min = new HashMap<String, Integer>();
        HashMap<String, Integer> max = new HashMap<String, Integer>();
        
        for (String file : files) {
            if (!file.startsWith("./"+dir+"/"))
                continue;
            
            String imgName = file.substring(file.lastIndexOf('/')+1);
            String[] name = imgName.split("\\.|_");
            // [Embryo10, EGFP, 000000, 00, tif]
            
            String basename = name[0];
            
            int channel = 0;
            if (name[1].equals("iRFP"))
                channel = 1;
            else if (name[1].equals("mCherry"))
                channel = 2;
            
            String rename = basename+"_C"+channel+"_T"+name[2]+".tif";
            
            int x = Integer.parseInt(name[2]);
            int low = min.containsKey(basename) ? min.get(basename) : x;
            if (x <= low) {
                low = x;
                min.put(basename, low);
            }
            int high = max.containsKey(basename) ? max.get(basename) : x;
            if (x >= high) {
                high = x;
                max.put(basename, high);
            }
            
            cmds.add("mv "+file+" ./"+dir+"/"+rename);
            addMapping(file, basename+".pattern");
        }
        
        NumberFormat nf = new DecimalFormat("00000");
        for(String basename : min.keySet()) {
            String pattern = basename+"_C<0-2>_T<"+nf.format(min.get(basename))+"-"+nf.format(max.get(basename))+">.tif";
            cmds.add("echo \""+pattern+"\" > ./"+dir+"/"+basename+".pattern");
        }
        
        return cmds;
    }
    
    public static List<String> SupplementaryFigure02(List<String> files) {
        // 0min.embryo1.tif
        
        String dir = "SupplementaryFigure02";
        
        List<String> cmds = new ArrayList<String>();
        
        HashSet<String> names = new HashSet<String>();
        
        for (String file : files) {
            if (!file.startsWith("./"+dir+"/"))
                continue;
            
            String imgName = file.substring(file.lastIndexOf('/')+1);
            String[] name = imgName.split("\\.");
            
            String basename = name[1];
            names.add(basename);
            
            int t = 0;
            if (name[0].equals("7min"))
                t = 1;
            if (name[0].equals("8min"))
                t = 2;
            if (name[0].equals("9min"))
                t = 3;
            if (name[0].equals("10min"))
                t = 4;
            if (name[0].equals("11min"))
                t = 5;
            if (name[0].equals("12min"))
                t = 6;
            if (name[0].equals("13min"))
                t = 7;
            
            String rename = basename+"_T"+t+".tif";
            
            cmds.add("mv "+file+" ./"+dir+"/"+rename);
            addMapping(file, basename+".pattern");
        }
        
        NumberFormat nf = new DecimalFormat("00000");
        for(String basename : names) {
            String pattern = basename+"_T<0-7>.tif";
            cmds.add("echo \""+pattern+"\" > ./"+dir+"/"+basename+".pattern");
        }
        
        return cmds;
    }
    
    public static List<String> SupplementaryFigure05(List<String> files) {
        // Metaphase1.Cam_Right_00000 (16)-Enhancement Filter 2_T001_Z001_C01.tif
        
        String dir = "SupplementaryFigure05";
        
        List<String> cmds = new ArrayList<String>();
        
        HashSet<String> names = new HashSet<String>();
        
        HashMap<String, Integer> minZ = new HashMap<String, Integer>();
        HashMap<String, Integer> maxZ = new HashMap<String, Integer>();
        
        HashMap<String, Integer> minT = new HashMap<String, Integer>();
        HashMap<String, Integer> maxT = new HashMap<String, Integer>();
        
        for (String file : files) {
            if (!file.startsWith("./"+dir+"/"))
                continue;
            
            String imgName = file.substring(file.lastIndexOf('/')+1);
            String[] name = imgName.split("\\.|_");
            // [Metaphase1, Cam, Right, 00000 (16)-Enhancement Filter 2, T001, Z001, C01, tif]
            
            String basename = name[0];
            names.add(basename);
            
            addMapping(file, basename+".pattern");
            
            String z = name[5].replace("Z", "");
            String t = name[4].replace("T", "");
            
            int x = Integer.parseInt(z);
            int low = minZ.containsKey(basename) ? minZ.get(basename) : x;
            if (x <= low) {
                low = x;
                minZ.put(basename, low);
            }
            int high = maxZ.containsKey(basename) ? maxZ.get(basename) : x;
            if (x >= high) {
                high = x;
                maxZ.put(basename, high);
            }
            
            x = Integer.parseInt(t);
            low = minT.containsKey(basename) ? minT.get(basename) : x;
            if (x <= low) {
                low = x;
                minT.put(basename, low);
            }
            high = maxT.containsKey(basename) ? maxT.get(basename) : x;
            if (x >= high) {
                high = x;
                maxT.put(basename, high);
            }
        }
        
        NumberFormat nf = new DecimalFormat("000");
        for(String basename : names) {
            String pattern = basename+".*_T<"+nf.format(minT.get(basename))+"-"+nf.format(maxT.get(basename))+">_Z<"+nf.format(minZ.get(basename))+"-"+nf.format(maxZ.get(basename))+">.*.tif";
            cmds.add("echo \""+pattern+"\" > ./"+dir+"/"+basename+".pattern");
        }
        
        return cmds;
    }
    
    
    public static List<String> SupplementaryFigure06A(List<String> files) {
        // 4Cell.Cell07-2.Position_0.stack_0.iRFP.000295_00.tif
        // 4Cell.Cell07-2.Position_0.stack_0.iRFP.Ext.000293_00.tif
        
        String dir = "SupplementaryFigure06A";
        
        List<String> cmds = new ArrayList<String>();
        
        HashSet<String> names = new HashSet<String>();
        
        for (String file : files) {
            if (!file.startsWith("./"+dir+"/"))
                continue;
            
            String imgName = file.substring(file.lastIndexOf('/')+1);
            
            String[] name = imgName.split("\\.");
            // [2Cell, Cell01-1, Position_0, stack_0, EGFP, 000086_00, tif]
            
            String basename = name[0]+"."+name[1]+"."+name[2]+"."+name[3];
            String t = "";
            if (name[5].equals("Ext")) {
                basename += "."+name[5]+".T"+name[6];
                t = name[6];
            }
            else {
                basename += ".T"+name[5];
                t = name[5];
            }
            
            t = t.substring(0, t.indexOf('_'));
            
            names.add(basename);
            
            int channel = 0;
            if (name[4].equals("iRFP"))
                channel = 1;
            else if (name[4].equals("mCherry"))
                channel = 2;
            
            String rename = basename+"_C"+channel+".tif";
            
            cmds.add("mv "+file+" ./"+dir+"/"+rename);
            addMapping(file, basename+".pattern");
        }
        
        for(String basename : names) {
            String pattern = basename+"_C<0-2>.tif";
            cmds.add("echo \""+pattern+"\" > ./"+dir+"/"+basename+".pattern");
        }
        
        return cmds;
    }
    
    public static List<String> SupplementaryFigure06H(List<String> files) {
        // 02_Cell_Stage.Embryo22.Alexa_647.000000_00.tif
        // 02_Cell_Stage.Embryo22_C0.tif
        
        String dir = "SupplementaryFigure06H";
        
        List<String> cmds = new ArrayList<String>();
        
        HashSet<String> names = new HashSet<String>();
        
        for (String file : files) {
            if (!file.startsWith("./"+dir+"/"))
                continue;
            
            String imgName = file.substring(file.lastIndexOf('/')+1);
            
            String[] name = imgName.split("\\.|_");
            // [02, Cell, Stage, Embryo15, Alexa, 647, 000000, 00, tif]
            
            String basename = name[0]+"_"+name[1]+"_"+name[2]+"."+name[3];
            names.add(basename);
            
            int channel = 0;
            if (name[4].equals("Alexa"))
                channel = 1;
            
            String rename = basename+"_C"+channel+"_T0.tif";
            
            cmds.add("mv "+file+" ./"+dir+"/"+rename);
            addMapping(file, basename+".pattern");
        }
        
        for(String basename : names) {
            String pattern = basename+"_C<0-1>_T<0>.tif";
            cmds.add("echo \""+pattern+"\" > ./"+dir+"/"+basename+".pattern");
        }
        
        return cmds;
    }
    
    public static List<String> SupplementaryFigure08(List<String> files) {
        // NocMo.Washout.Embryo1.EGFP.Cam_Right_00122.tif  
        // NocMo.Washout.Embryo1_C0_Z00122.tif
        
        String dir = "SupplementaryFigure08";
        
        List<String> cmds = new ArrayList<String>();
        
        HashMap<String, Integer> min = new HashMap<String, Integer>();
        HashMap<String, Integer> max = new HashMap<String, Integer>();
        
        for (String file : files) {
            if (!file.startsWith("./"+dir+"/"))
                continue;
            
            String imgName = file.substring(file.lastIndexOf('/')+1);
            
            String[] name = imgName.split("\\.|_");
            // [MoNoc, Monastrol, Embryo1, EGFP, Cam, Right, 00000, tif]
            
            String basename = name[0]+"."+name[1]+"."+name[2];
            
            int channel = 0;
            if (name[3].equals("iRFP"))
                channel = 1;
            else if (name[3].equals("mCherry"))
                channel = 2;
            
            String rename = basename+"_C"+channel+"_T"+name[6]+".tif";
            
            int x = Integer.parseInt(name[6]);
            int low = min.containsKey(basename) ? min.get(basename) : x;
            if (x <= low) {
                low = x;
                min.put(basename, low);
            }
            int high = max.containsKey(basename) ? max.get(basename) : x;
            if (x >= high) {
                high = x;
                max.put(basename, high);
            }
            
            cmds.add("mv "+file+" ./"+dir+"/"+rename);
            addMapping(file, basename+".pattern");
        }
        
        NumberFormat nf = new DecimalFormat("00000");
        for(String basename : min.keySet()) {
            String pattern = basename+"_C<0-2>_T<"+nf.format(min.get(basename))+"-"+nf.format(max.get(basename))+">.tif";
            cmds.add("echo \""+pattern+"\" > ./"+dir+"/"+basename+".pattern");
        }
        
        return cmds;
    }
    
    public static List<String> SupplementaryFigure09(List<String> files) {
        // MoNoc.2_Cell.20.tif
        // MoNoc.2_Cell.T20.tif
        
        String dir = "SupplementaryFigure09";
        
        List<String> cmds = new ArrayList<String>();
        
        HashMap<String, Integer> min = new HashMap<String, Integer>();
        HashMap<String, Integer> max = new HashMap<String, Integer>();
        
        for (String file : files) {
            if (!file.startsWith("./"+dir+"/"))
                continue;
            
            String imgName = file.substring(file.lastIndexOf('/')+1);
            
            String[] name = imgName.split("\\.");
            // [MoNoc, 2, Cell, 01, tif]
            
            String basename = name[0]+"."+name[1];
            
            String rename = basename+"_T"+name[2]+".tif";
            
            int x = Integer.parseInt(name[2]);
            int low = min.containsKey(basename) ? min.get(basename) : x;
            if (x <= low) {
                low = x;
                min.put(basename, low);
            }
            int high = max.containsKey(basename) ? max.get(basename) : x;
            if (x >= high) {
                high = x;
                max.put(basename, high);
            }
            
            cmds.add("mv "+file+" ./"+dir+"/"+rename);
            addMapping(file, basename+".pattern");
        }
        
        NumberFormat nf = new DecimalFormat("00");
        for(String basename : min.keySet()) {
            String pattern = basename+"_T<"+nf.format(min.get(basename))+"-"+nf.format(max.get(basename))+">.tif";
            cmds.add("echo \""+pattern+"\" > ./"+dir+"/"+basename+".pattern");
        }
        
        return cmds;
    }
    
    public static List<String> SupplementaryFigure10A(List<String> files) {
        // MoNoc.2_Cell.20.tif
        // MoNoc.2_Cell.T20.tif
        
        String dir = "SupplementaryFigure10A";
        
        List<String> cmds = new ArrayList<String>();
        
        HashMap<String, Integer> min = new HashMap<String, Integer>();
        HashMap<String, Integer> max = new HashMap<String, Integer>();
        
        for (String file : files) {
            if (!file.startsWith("./"+dir+"/"))
                continue;
            
            String imgName = file.substring(file.lastIndexOf('/')+1);
            
            String[] name = imgName.split("\\.");
            // [MoNoc, 2, Cell, 01, tif]
            
            String basename = name[0]+"."+name[1];
            
            String rename = basename+"_T"+name[2]+".tif";
            
            int x = Integer.parseInt(name[2]);
            int low = min.containsKey(basename) ? min.get(basename) : x;
            if (x <= low) {
                low = x;
                min.put(basename, low);
            }
            int high = max.containsKey(basename) ? max.get(basename) : x;
            if (x >= high) {
                high = x;
                max.put(basename, high);
            }
            
            cmds.add("mv "+file+" ./"+dir+"/"+rename);
            addMapping(file, basename+".pattern");
        }
        
        NumberFormat nf = new DecimalFormat("00");
        for(String basename : min.keySet()) {
            String pattern = basename+"_T<"+nf.format(min.get(basename))+"-"+nf.format(max.get(basename))+">.tif";
            cmds.add("echo \""+pattern+"\" > ./"+dir+"/"+basename+".pattern");
        }
        
        return cmds;
    }
    
    public static List<String> SupplementaryFigure10B(List<String> files) {
        // MoNoc.2_Cell.20.tif
        // MoNoc.2_Cell.T20.tif
        
        String dir = "SupplementaryFigure10B";
        
        List<String> cmds = new ArrayList<String>();
        
        HashMap<String, Integer> min = new HashMap<String, Integer>();
        HashMap<String, Integer> max = new HashMap<String, Integer>();
        
        for (String file : files) {
            if (!file.startsWith("./"+dir+"/"))
                continue;
            
            String imgName = file.substring(file.lastIndexOf('/')+1);
            
            String[] name = imgName.split("\\.");
            // [MoNoc, 2, Cell, 01, tif]
            
            String basename = name[0]+"."+name[1];
            
            String rename = basename+"_T"+name[2]+".tif";
            
            int x = Integer.parseInt(name[2]);
            int low = min.containsKey(basename) ? min.get(basename) : x;
            if (x <= low) {
                low = x;
                min.put(basename, low);
            }
            int high = max.containsKey(basename) ? max.get(basename) : x;
            if (x >= high) {
                high = x;
                max.put(basename, high);
            }
            
            cmds.add("mv "+file+" ./"+dir+"/"+rename);
            addMapping(file, basename+".pattern");
        }
        
        NumberFormat nf = new DecimalFormat("00");
        for(String basename : min.keySet()) {
            String pattern = basename+"_T<"+nf.format(min.get(basename))+"-"+nf.format(max.get(basename))+">.tif";
            cmds.add("echo \""+pattern+"\" > ./"+dir+"/"+basename+".pattern");
        }
        
        return cmds;
    }
    
    public static void main(String[] args) throws IOException {
        
        String tmp = CSVTools.readFile("/Users/dlindner/Repositories/idr0045-reichmann-zygotespindle/filelisting.txt");
        List<String> files = new ArrayList<String>();
        for (String s : tmp.split("\n")) {
            files.add(s);
        }
        
        List<String> cmds = new ArrayList<String>();
        
        cmds.addAll(Figure01A(files));

        //cmds.addAll(Figure01C(files));  // import images as they are

        cmds.addAll(Figure02(files));

        cmds.addAll(Figure03(files));

        cmds.addAll(Figure04(files));

        cmds.addAll(SupplementaryFigure01(files));

        cmds.addAll(SupplementaryFigure02(files));

        cmds.addAll(SupplementaryFigure05(files));

        cmds.addAll(SupplementaryFigure06A(files));

        cmds.addAll(SupplementaryFigure06H(files));

        cmds.addAll(SupplementaryFigure08(files));

        cmds.addAll(SupplementaryFigure09(files));

        cmds.addAll(SupplementaryFigure10A(files));

        cmds.addAll(SupplementaryFigure10B(files));
        
        
        StringBuilder sb = new StringBuilder();
        sb.append("#!/bin/bash\n\n");
        for (String s : cmds) {
                 sb.append(s+"\n");
        }
        CSVTools.writeFile("/Users/dlindner/Repositories/idr0045-reichmann-zygotespindle/scripts/import/renameCommands.sh", sb.toString());
        
        sb = new StringBuilder();
        sb.append(BasicCSVUtils.join(new String[]{"Dataset Name", "Image File", "Image Name"})+"\n");
        for(Entry<String, String> e : mapping.entrySet())  {
            String[] parts = new String[3];
            parts[0] = e.getKey().substring(0, e.getKey().indexOf('/'));
            parts[1] = e.getKey().substring(e.getKey().indexOf('/')+1);
            parts[2] = e.getValue();
            sb.append(BasicCSVUtils.join(parts)+"\n");
        }
        CSVTools.writeFile("/Users/dlindner/Repositories/idr0045-reichmann-zygotespindle/experimentA/filenameImageMapping.csv", sb.toString());
    }

}
