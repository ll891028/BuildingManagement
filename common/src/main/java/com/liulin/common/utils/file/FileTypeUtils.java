package com.liulin.common.utils.file;

import java.io.File;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 文件类型工具类
 *
 * @author liulin
 */
public class FileTypeUtils
{

    private static String[] image ={"gif","jpg","png","psd","bmp"};

    private static String[] html ={"html","jsp","php"};

    private static String[] text ={"txt"};

    private static String[] office ={"doc","wps","docx","xls","xlsx","pdf"};

    private static String[] video = {"wmv","asf","rm","rmvb","mov","mp4","avi"};

    private static String[] audio = {"wav","mp3"};

    private static String[] pdf = {"pdf"};

    private static String[] other = {"rar","zip","z"};

    private static String IMAGE = "image";

    private static String HTML = "html";

    private static String TEXT = "text";

    private static String OFFICE = "office";

    private static String VIDEO = "video";

    private static String AUDIO = "audio";

    private static String PDF = "pdf";

    private static String OTHER = "other";



    /**
     * 获取文件类型
     * <p>
     * 例如: liulin.txt, 返回: txt
     * 
     * @param file 文件名
     * @return 后缀（不含".")
     */
    public static String getFileType(File file)
    {
        if (null == file)
        {
            return StringUtils.EMPTY;
        }
        return getFileType(file.getName());
    }

    /**
     * 获取文件类型
     * <p>
     * 例如: liulin.txt, 返回: txt
     *
     * @param fileName 文件名
     * @return 后缀（不含".")
     */
    public static String getFileType(String fileName)
    {
        int separatorIndex = fileName.lastIndexOf(".");
        if (separatorIndex < 0)
        {
            return "";
        }
        return fileName.substring(separatorIndex + 1).toLowerCase();
    }

    /**
     * 获取文件类型
     * 
     * @param photoByte 文件字节码
     * @return 后缀（不含".")
     */
    public static String getFileExtendName(byte[] photoByte)
    {
        String strFileExtendName = "JPG";
        if ((photoByte[0] == 71) && (photoByte[1] == 73) && (photoByte[2] == 70) && (photoByte[3] == 56)
                && ((photoByte[4] == 55) || (photoByte[4] == 57)) && (photoByte[5] == 97))
        {
            strFileExtendName = "GIF";
        }
        else if ((photoByte[6] == 74) && (photoByte[7] == 70) && (photoByte[8] == 73) && (photoByte[9] == 70))
        {
            strFileExtendName = "JPG";
        }
        else if ((photoByte[0] == 66) && (photoByte[1] == 77))
        {
            strFileExtendName = "BMP";
        }
        else if ((photoByte[1] == 80) && (photoByte[2] == 78) && (photoByte[3] == 71))
        {
            strFileExtendName = "PNG";
        }
        return strFileExtendName;
    }

    public static String getFileTypeByExt(String ext){
        if(ArrayUtils.contains(image,ext)){
            return FileTypeUtils.IMAGE;
        }else if(ArrayUtils.contains(html,ext)){
            return FileTypeUtils.HTML;
        }else if(ArrayUtils.contains(text,ext)){
            return FileTypeUtils.TEXT;
        }else if(ArrayUtils.contains(office,ext)){
            return FileTypeUtils.OFFICE;
        }else if(ArrayUtils.contains(pdf,ext)){
            return FileTypeUtils.PDF;
        }else if(ArrayUtils.contains(video,ext)){
            return FileTypeUtils.VIDEO;
        }else if(ArrayUtils.contains(audio,ext)){
            return FileTypeUtils.AUDIO;
        }else if(ArrayUtils.contains(other,ext)){
            return FileTypeUtils.OTHER;
        }
        return FileTypeUtils.OTHER;
    }
}