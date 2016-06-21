package com.jkarthus.joe.demo.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author shihuajian
 * @depict 压缩包工具
 * @since 6/21/16 3:54 PM
 */
public class ZipUtils {

    /** 提取ZIP压缩包的comment */
    public static String extractZipComment(String filename) {
        String retStr = null;
        try {
            File file = new File(filename);
            int fileLen = (int) file.length();

            FileInputStream in = new FileInputStream(file);

			/* The whole ZIP comment (including the magic byte sequence)
            * MUST fit in the buffer
			* otherwise, the comment will not be recognized correctly
			*
			* You can safely increase the buffer size if you like
			*/
            byte[] buffer = new byte[Math.min(fileLen, 8192)];
            int len;

            in.skip(fileLen - buffer.length);

            if ((len = in.read(buffer)) > 0) {
                retStr = getZipCommentFromBuffer(buffer, len);
            }

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TextUtils.isEmpty(retStr) ? "ERROR! ZIP comment NOT found!" : retStr;
    }

    private static String getZipCommentFromBuffer(byte[] buffer, int len) {
        byte[] magicDirEnd = {0x50, 0x4b, 0x05, 0x06};
        int buffLen = Math.min(buffer.length, len);
        //Check the buffer from the end
        for (int i = buffLen - magicDirEnd.length - 22; i >= 0; i--) {
            boolean isMagicStart = true;
            for (int k = 0; k < magicDirEnd.length; k++) {
                if (buffer[i + k] != magicDirEnd[k]) {
                    isMagicStart = false;
                    break;
                }
            }
            if (isMagicStart) {
                //Magic Start found!
                int commentLen = buffer[i + 20] + buffer[i + 22] * 256;
                int realLen = buffLen - i - 22;
                System.out.println("ZIP comment found at buffer position " + (i + 22) + " with len=" + commentLen + ", good!");
                if (commentLen != realLen) {
                    System.out.println("WARNING! ZIP comment size mismatch: directory says len is " +
                            commentLen + ", but file ends after " + realLen + " bytes!");
                }
                String comment = new String(buffer, i + 22, Math.min(commentLen, realLen));
                return comment;
            }
        }
        System.out.println("ERROR! ZIP comment NOT found!");
        return null;
    }

}
