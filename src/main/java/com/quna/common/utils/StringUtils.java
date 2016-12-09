package com.quna.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.quna.common.exception.runtime.QunaRuntimeException;
/**
 * 
 * <b>字符串辅助工具.</b>
 * <b>Description:</b> 主要提供如下: 
 *  1、判断字符串有有效性, 如长度、文档内容等;
 *  2、判断字符串中是否还有特殊字符, 对特殊支持进行统计、替换等操作;
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2014-1-1 上午10:00:01
 * <b>Copyright:</b> Copyright &copy;2006-2015 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2014-01-01 10:00:01     252054576@qq.com
 *         new file.
 * </pre>
 */
public abstract class StringUtils extends _Util {

    /**
     * 固定字符串"defalut".
     */
    public static final String DEFAULT = "default";

    /**
     * null字符串固定字符串"<null>".
     */
    public static final String NULL_STR = "<null>";

    /**
     * 当前目录:"."
     */
    public static final String CURRENT_PATH = ".";

    /**
     * 上级目录:".."
     */
    public static final String TOP_PATH = "..";

    /**
     * 通用文件路径的分隔符:反斜杠 "/".
     */
    public static final String FOLDER_SEPARATOR = "/";

    /**
     * Windows平台下文件路径的分隔符:双斜杠 "\\".
     */
    public static final String FOLDER_SEPARATOR_WINDOWS = "\\";

    /**
     * 文件、URL等后缀的分割符: . 例如：1.jpg、index.html.
     */
    public static final String EXTENSION_SEPARATOR = ".";    
    

    /**
     * 随机字符串的种子
     */
    public static final char RANDOM_CHAR_SEEDS[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_".toCharArray();
    
    /**
     * 随机数字种子
     */
    public static final char RANDOM_NUMBER_SEEDS[] = "0123456789".toCharArray();

    // -------------
    // ------------- General convenience methods for working with Strings
    /**
     * <pre>
     * 检查给出的字符串是否为空.
     * 
     * 如果给出的字符串为null 或 "", 则返回true.
     * 例如: 
     * StringUtil.isEmpty(null)    = true
     * StringUtil.isEmpty("")      = true
     * StringUtil.isEmpty(" ")     = false
     * StringUtil.isEmpty("abc")   = false
     * StringUtil.isEmpty(" abc")  = false
     * StringUtil.isEmpty("abc ")  = false
     * StringUtil.isEmpty("　abc") = false
     * StringUtil.isEmpty("abc　") = false
     * </pre>
     * 
     * @param cs 给出的字符串.
     * @return boolean 是否为null 或 "".
     * @see java.lang.Character#isWhitespace
     */
    public static boolean isEmpty(CharSequence cs) {
        return (cs == null || cs.length() == 0);
    }

    /**
     * <pre>
     * 检查给出的字符串是空白（包括中文空格）.
     * 
     * 如果给出的字符串为null 或 "" 或一个空格（包括中文空格）字符, 则返回true.
     * 例如: 
     * StringUtil.isBlank(null)    = true
     * StringUtil.isBlank("")      = true
     * StringUtil.isBlank(" ")     = true
     * StringUtil.isBlank("　")    = true
     * StringUtil.isBlank("abc")   = false
     * StringUtil.isBlank(" abc")  = false
     * StringUtil.isBlank("abc ")  = false
     * StringUtil.isBlank("　abc") = false
     * StringUtil.isBlank("abc　") = false
     * </pre>
     * 
     * @param cs 给出的字符串.
     * @return boolean 是否为null 或 "" 或一个空格（包括中文空格）.
     * @see java.lang.Character#isWhitespace
     */
    public static boolean isBlank(CharSequence cs) {

        int strLen;

        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }

        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * <pre>
     * 检查给出的字符串是否至少拥有一个非空格（包括中文空格）字符.
     * 
     * 如果给出的字符串为null 或 "", 则返回false.
     * 例如: 
     * StringUtil.hasText(null)    == false;
     * StringUtil.hasText("")      == false;
     * StringUtil.hasText(" ")     == false;
     * StringUtil.hasText("　")    == false;
     * StringUtil.hasText("abc")   == true;
     * StringUtil.hasText("abc")   == true;
     * StringUtil.hasText(" abc ") == true;
     * </pre>
     * 
     * @param str 给出的字符串.
     * @return boolean 是否至少拥有一个非空格（包括中文空格）字符.
     * @see java.lang.Character#isWhitespace
     */
    public static boolean hasText(String str) {

        int len = 0;

        if (null == str || (len = str.length()) == 0) {
            return false;
        }

        // 依次判断每个字符是否为空格（中文）, 如果为空格则返回false.
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * <pre>
     * 检查给出的字符串是否有长度.
     * 
     * 如果给出的字符串为null 或 "", 则返回false.
     * 例如: 
     * StringUtil.hasLength(null)   == false;
     * StringUtil.hasLength("")     == false;
     * StringUtil.hasLength(" ")    == true;
     * StringUtil.hasLength("　")   == true;
     * StringUtil.hasLength("abc")  == true;
     * StringUtil.hasLength("abc ") == true;
     * StringUtil.hasLength(" abc") == true;
     * </pre>
     * 
     * @param str 给出的字符串.
     * @return boolean 是否有长度.
     */
    public static boolean hasLength(String str) {
        // 主要判断字符串对象不为null, 同时其字符长度大于等于 1, 则返回true
        return (null != str && str.length() >= 1);
    }

    /**
     * <pre>
     * 将给定的字符串去除空格.
     * 
     * 如果给出的字符串为null, 则返回null.
     * 例如: 
     * StringUtil.trim(null)    = null
     * StringUtil.trim("")      = ""
     * StringUtil.trim(" ")     = ""
     * StringUtil.trim("　")    = ""
     * StringUtil.trim("abc")   = "abc"
     * StringUtil.trim(" abc")  = "abc"
     * StringUtil.trim("abc ")  = "abc"
     * StringUtil.trim("　abc") = "abc"
     * StringUtil.trim("abc　") = "abc"
     * </pre>
     * 
     * @param str 给出的字符串.
     * @return String 去除空格后的字符串.
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * <pre>
     * 安全访问给出的字符串, 
     * 如果对象为Null, 则将返回缺省StringUtil.DEFAULT_STR = "";
     * 
     * StringUtil.nullSafeString(null)  = "";
     * StringUtil.nullSafeString("  ")  = "";
     * StringUtil.nullSafeString("abc") = "abc";
     * </pre>
     * 
     * @param str 给出的字符串
     * @return 字符串
     */
    public static String nullSafeString(String str) {
        return nullSafeString(str, EMPTY_STR);
    }

    /**
     * <pre>
     * 安全访问给出的字符串, 
     * 如果对象为Null, 则将返回缺省默认值 
     * 
     * StringUtil.nullSafeString(null, "a")  >>  "a";
     * StringUtil.nullSafeString("  ", "a")  >>  "  ";
     * StringUtil.nullSafeString("abc", "a") >>  "abc";
     * </pre>
     * 
     * @param str 给出的字符串
     * @param defaultValue 缺省默认值
     * @return 字符串
     */
    public static String nullSafeString(String str, String defaultValue) {
        return (null == str) ? defaultValue : str;
    }

    /**
     * <pre>
     * 检查给出的两个字符串是否内容（忽略大小写）相同, 
     * 任意一个字符串为Null时, 返回false.
     * 
     * 如：
     * StringUtil.equalsWith("", "")       = true
     * StringUtil.equalsWith(" ", " ")     = true
     * StringUtil.equalsWith("", " ")      = false
     * StringUtil.equalsWith("abc", "abc") = true
     * StringUtil.equalsWith("aBc", "AbC") = true
     * StringUtil.equalsWith("aBc", null)  = false
     * </pre>
     * 
     * @param str1 给出第一个的字符串. 如果为null, 则返回false.
     * @param str2 给出第二个的字符串. 如果为null, 则返回false.
     * @return boolean 是否内容相同.
     */
    public static boolean equals(String str1, String str2) {
        return equals(str1, str2, true);
    }

    /**
     * <pre>
     * 检查给出的两个字符串是否内容相同,  验证时指定是否区分大小写, 
     * 当任意一个字符串为Null时, 返回false.
     * 
     * 如：
     * StringUtil.equalsWith("aBc", "AbC", fasle) = fasle
     * StringUtil.equalsWith("aBc", null, true)   = false
     * </pre>
     * 
     * @param str1 给出的第一个字符串. 如果为null, 则返回false.
     * @param str2 给出的第二个字符串. 如果为null, 则返回false.
     * @param ignoreCase 是否忽略字符大小写进行匹配, true:区分大小写；false:忽略大小写.
     * @return boolean 是否内容相同.
     */
    public static boolean equals(String str1, String str2, boolean ignoreCase) {

        if (null == str1 || null == str2) {
            return false;
        }

        // 判断是否需要忽略大小写进行比较
        if (ignoreCase) {
            str1 = str1.toLowerCase();
            str2 = str2.toLowerCase();
        }

        return str1.equals(str2);
    }

    /**
     * <pre>
     * 检查给出的字符串是否以特定的字符前缀（忽略大小写）开始.
     * 
     * 如果给出的字符串为null 或 "", 则返回false, 为""时是因长度小于前缀;
     * 如果特定的前缀为null 或 "", 则返回false;
     * 如果给出的字符串长度小于特定的字符前缀长度时, 则返回false.
     * 例如: 
     * StringUtil.startsWith("abc", null)   == false;
     * StringUtil.startsWith("abc", " ")    == false;
     * StringUtil.startsWith("abc", "　")   == false;
     * StringUtil.startsWith("abc", "aBc ") == false;
     * StringUtil.startsWith("abc", " aBc") == false;
     * StringUtil.startsWith("abc", "")     == true;
     * StringUtil.startsWith("abc", "abc")  == false;
     * </pre>
     * 
     * @param str 给出的字符串.
     * @param prefix 特定的前缀.
     * @return boolean 是否为前缀.
     * @see java.lang.String#startsWith
     */
    public static boolean startsWith(String str, String prefix) {
        return startsWith(str, prefix, false);
    }

    /**
     * <pre>
     * 检查给出的字符串是否以特定的字符前缀开始, 验证时需指定是否区分大小写.
     * 
     * 如果给出的字符串为null 或 "", 则返回false, 为""时是因长度小于前缀;
     * 如果特定的前缀为null 或 "", 则返回false;
     * 如果给出的字符串长度小于特定的字符前缀长度时, 则返回false.
     * 例如: 
     * StringUtil.startsWith(" abc", "aBc", true) == false;
     * StringUtil.startsWith("abc", null, true)   == false;
     * StringUtil.startsWith("abc", " ", true)    == false;
     * StringUtil.startsWith("abc", "　", true)   == false;
     * StringUtil.startsWith("abc", "", true)     == true;
     * StringUtil.startsWith("abc", "abc", true)  == true;
     * </pre>
     * 
     * @param str 给出的字符串.
     * @param prefix 特定的前缀.
     * @param ignoreCase 是否忽略字符大小写进行匹配, true: 区分大小写; false: 忽略大小写.
     * @return boolean 是否为前缀.
     * @see java.lang.String#startsWith
     */
    public static boolean startsWith(String str, String prefix, boolean ignoreCase) {

        if (null == str || null == prefix) {
            return false;
        }

        if (str.startsWith(prefix)) {
            return true;
        }

        if (str.length() < prefix.length()) {
            return false;
        }

        // 截取被检查字符串头部与前缀长度相等长度的字符串.
        String sub = str.substring(0, prefix.length());

        // 判断是否需要忽略大小写进行比较.
        if (ignoreCase) {
            sub 	= sub.toLowerCase();
            prefix 	= prefix.toLowerCase();
        }

        // 判断截取的头部字符串部分是否与前缀相同, 即为此前缀开始的
        return sub.equals(prefix);
    }

    /**
     * <pre>
     * 检查给出的字符串是否以特定的字符后缀（忽略大小写）结尾.
     * 
     * 如果给出的字符串为null 或 "", 则返回false, 为""时是因长度小于后缀;
     * 如果特定的后缀为null 或 "", 则返回false;
     * 如果给出的字符串长度小于特定的字符后缀长度时, 则返回false.
     * 例如: 
     * StringUtil.endsWith("abc", "")     == true;
     * StringUtil.endsWith("aBc", "aBc")  == true);
     * StringUtil.endsWith("abc", " ")    == false;
     * StringUtil.endsWith("abc", "　")   == false;
     * StringUtil.endsWith("abc", "aBc ") == false;
     * </pre>
     * 
     * @param str 给出的字符串.
     * @param suffix 特定的后缀.
     * @return boolean 是否为后缀.
     * @see java.lang.String#endsWith
     */
    public static boolean endsWith(String str, String suffix) {
        return endsWith(str, suffix, false);
    }

    /**
     * <pre>
     * 检查给出的字符串是否以特定的字符后缀结尾, 验证时需指定是否区分大小写.
     * 
     * 如果给出的字符串为null 或 "", 则返回false, 为""时是因长度小于后缀;
     * 如果特定的后缀为null 或 "", 则返回false;
     * 如果给出的字符串长度小于特定的字符后缀长度时, 则返回false.
     * 例如: 
     * StringUtil.startsWith(" abc", "aBc", true) == false;
     * StringUtil.startsWith("abc", null, true)   == false;
     * StringUtil.startsWith("abc", " ", true)    == false;
     * StringUtil.startsWith("abc", "　", true)   == false;
     * StringUtil.startsWith("abc ", "aBc", true) == true;
     * StringUtil.startsWith("abc", "", true)     == true;
     * StringUtil.startsWith("abc", "abc", true)  == true;
     * </pre>
     * 
     * @param str 给出的字符串.
     * @param suffix 特定的后缀.
     * @param ignoreCase 是否忽略字符大小写进行匹配, true: 区分大小写; false: 忽略大小写.
     * @return boolean 是否为后缀.
     * @see java.lang.String#endsWith
     */
    public static boolean endsWith(String str, String suffix, boolean ignoreCase) {

        if (null == str || null == suffix) {
            return false;
        }

        if (str.endsWith(suffix)) {
            return true;
        }

        if (str.length() < suffix.length()) {
            return false;
        }

        // 截取被检查字符串末尾与后缀长度相等长度的字符串.
        String sub = str.substring(str.length() - suffix.length());

        // 判断是否需要忽略大小写进行比较
        if (ignoreCase) {
            sub = sub.toLowerCase();
            suffix = suffix.toLowerCase();
        }

        // 判断截取的结尾字符串部分是否与后缀相同, 即为此后缀开始的
        return sub.equals(suffix);
    }

    /**
     * <pre>
     * 检查给出的字符串中含有关键词（特定的子串）（忽略大小写）出现的次数.
     * 
     * 如果任何一个参数为null或长度为0时, 则直接返回0.
     * 例如: 
     * StringUtil.countKey(null, "Bc")        = 0
     * StringUtil.countKey("abc", null)       = 0
     * StringUtil.countKey("abc", "Bc")       = 0
     * StringUtil.countKey("abclcbcdc", "bc") = 2
     * </pre>
     * 
     * @param str 给出的字符串.
     * @param fix 特定的子串.
     * @return int 出现的次数
     */
    public static int countKey(String str, String fix) {
        return countKey(str, fix, false);
    }

    /**
     * <pre>
     * 检查给出的字符串中含有关键词（特定的子串）出现的次数, 统计时需指定是否区分大小写.
     * 
     * 如果任何一个参数为null或长度为0时, 则直接返回0.
     * 例如: 
     * StringUtil.countKey(null, "Bc", true)        = 0
     * StringUtil.countKey("abc", null, true)       = 0
     * StringUtil.countKey("abc", "Bc", true)       = 0
     * StringUtil.countKey("abclcBcdc", "bc", true) = 1
     * </pre>
     * 
     * @param str 给出的字符串.
     * @param fix 特定的子串.
     * @param ignoreCase 是否忽略字符大小写进行匹配, true: 区分大小写; false: 忽略大小写.
     * @return int 出现的次数
     */
    public static int countKey(String str, String fix, boolean ignoreCase) {

        if (null == str || null == fix || str.length() == 0 || fix.length() == 0) {
            return 0;
        }

        // 判断是否需要忽略大小写进行统计
        if (ignoreCase) {
            str = str.toLowerCase();
            fix = fix.toLowerCase();
        }

        int count = 0, index = 0, pos = 0;
        while ((index = str.indexOf(fix, pos)) != -1) {

            ++count;
            pos = index + fix.length();
        }

        return count;
    }

    /**
     * <pre>
     * 将给出的字符串中关键词（特定的子串）全部替换为另一个关键词（特定的子串）.
     * 
     * 如果给出的字符串为null, 则直接返回 给出的字符串;
     * 如果被匹配关键词（特定的子串）和替换关键词（特定的子串）, 任何一个为null时, 则直接返回 null.
     * 例如:
     * StringUtil.replace(null, "Aa", "")				= null
     * StringUtil.replace("", "Aa", "")					= ""
     * StringUtil.replace("Aa", null, "")				= "Aa"
     * StringUtil.replace("Aa", "", null)				= "Aa"
     * StringUtil.replace("Aa", "", "a")				= "Aa"
     * StringUtil.replace("abcccacbcsercb", "bc", "--") = "a--ccac--sercb"
     * StringUtil.replace("abcccacbcsercb", "bc", "")   = "accacsercb"
     * </pre>
     * 
     * @param str 给出的字符串.
     * @param oldPattern 被匹配关键词（特定的子串）
     * @param newPattern 替换关键词（特定的子串）
     * @return String 替换后的字符串
     */
    public static String replace(String str, String oldPattern, String newPattern) {

        if (null == str || null == oldPattern || null == newPattern || oldPattern.length() == 0) {
            return str;
        }

        int pos = 0;
        int patLen = oldPattern.length();
        int index = str.indexOf(oldPattern);

        StringBuffer sb = new StringBuffer();
        while (index >= 0) {

            sb.append(str.substring(pos, index));
            sb.append(newPattern);
            pos = index + patLen;
            index = str.indexOf(oldPattern, pos);
        }
        sb.append(str.substring(pos));

        return sb.toString();
    }

    /**
     * <pre>
     * 将给出的字符串中关键词（特定的子串）部分清洗掉, 
     * 任何一个参数为null时, 则直接返回给出的字符串. 
     * 
     * StringUtil.replace("abcccacbcsercb", "bc") = "accacsercb"
     * StringUtil.replace("abcccacbcsercb", "mm") = "abcccacbcsercb"
     * </pre>
     * 
     * @param str 给出的字符串. 如果为null, 则返回null.
     * @param pattern 被匹配关键词（特定的子串）.
     * @return String 替换后的字符串.
     */
    public static String delete(String str, String pattern) {
        return replace(str, pattern, "");
    }

    /**
     * <pre>
     * 将给出的字符串种的每个字符与需要匹配删除的字符集合进行比对清洗, 
     * 任何一个参数为null时, 则直接返回给出的字符串. 
     * 
     * StringUtil.deleteChar("abcccacbcsercb", "bc") = "aaser"
     * StringUtil.deleteChar("abcccacbcsercb", "mm") = "abcccacbcsercb"
     * </pre>
     * 
     * @param str 给出的字符串.
     * @param chars 需要从字符串中删除的字符集合.
     * @return String 删除匹配字符后的字符串.
     */
    public static String deleteChar(String str, String chars) {

        if (null == str || null == chars) {
            return str;
        }

        StringBuffer out = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {

            char c = str.charAt(i);
            if (chars.indexOf(c) == -1) {
                out.append(c);
            }
        }

        return out.toString();
    }

    // -------------
    // ------------- Convenience methods for working with formatted Strings
    /**
     * <pre>
     * 用单引号将给出的字符串括起来,
     * 如果给出的字符串为null, 则返回null.
     * 
     * StringUtil.quote(null) = null
     * StringUtil.quote("")   = "''"
     * StringUtil.quote(" ")  = "' '"
     * StringUtil.quote("a")  = "'a'"
     * 
     * <pre>
     * 
     * @param str 给出的字符串.
     * @return String 用单引号括起来后的字符串.
     */
    public static String quote(String str) {
        return quote(str, "'");
    }

    /**
     * <pre>
     * 用特定的字符串将给出的目标字符串括起来,
     * 如果给出的目标字符串或特定的字符串为null, 则返回null.
     * 
     * StringUtil.quote(null, "'") = null
     * StringUtil.quote("", "^")   = "^^"
     * StringUtil.quote(" ", "||") = "|| ||"
     * StringUtil.quote("a", "%")  = "%a%"
     * StringUtil.quote("a", null) = null
     * 
     * <pre>
     * 
     * @param str 给出的字符串.
     * @return String 用单引号括起来后的字符串.
     */
    public static String quote(String str, String fix) {

        if (null == str || null == fix) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(fix);
        sb.append(str);
        sb.append(fix);

        return sb.toString();
    }

    /**
     * <pre>
     * 将字符串的首字母变为大写, 其他字符不变.
     * 
     * 如果字符串为null 或 长度==0, 则直接返回该字符串.
     * </pre>
     * 
     * @param str 给出的字符串, 可以为null.
     * @return String 变换后的字符串.
     */
    public static String capitalize(String str) {

        if (null == str || str.length() == 0) {
            return str;
        }

        StringBuffer sb = new StringBuffer(str.length());
        sb.append(Character.toUpperCase(str.charAt(0)));
        sb.append(str.substring(1));

        return sb.toString();
    }

    /**
     * <pre>
     * 将字符串的首字母变为小写, 其他字符不变.
     * 
     * 如果字符串为null 或 长度==0, 则直接返回该字符串.
     * </pre>
     * 
     * @param str 给出的字符串, 可以为null.
     * @return String 变换后的字符串.
     */
    public static String uncapitalize(String str) {

        if (null == str || str.length() == 0) {
            return str;
        }

        StringBuffer sb = new StringBuffer(str.length());
        sb.append(Character.toLowerCase(str.charAt(0)));
        sb.append(str.substring(1));

        return sb.toString();
    }

    /**
     * <pre>
     * 将一个locale string变换成java.util.Locale对象.
     * 
     * 这是和Locales的toString方法相反的过程.
     * </pre>
     * 
     * @param str locale string.
     * @see java.util.Locale
     * @return Locale Locale对象实例.
     */
    public static Locale parseLocaleString(String str) {

        String[] array 	= tokenizeToArray(str, "_ ", false, false);

        String language = (array.length > 0 ? array[0] : "");
        String country 	= (array.length > 1 ? array[1] : "");
        String variant 	= (array.length > 2 ? array[2] : "");

        return (language.length() > 0 ? new Locale(language, country, variant) : null);
    }

    // -------------
    // ------------- Convenience methods for working with String arrays

    /**
     * <pre>
     * 用特定的分隔符拆分给出的字符串, 按照分隔符第一次出现的位置将字符串拆成前后两个子串.
     * 两个子串均不含分隔符.
     * 如果给出的字符串中不包含分隔符, 则返回null.
     * 
     * <pre>
     * 
     * @param str 给出的字符串, 可以为null.
     * @param separator 分隔符, 可以为null.
     * @return 拥有两个元素的字符串数组, 第一个元素为分隔符前的子串, 第二个元素为分隔符后的子串. 有可能为null.
     */
    public static String[] split(String str, String separator) {

        if (!hasLength(str) || !hasLength(separator)) {
            return null;
        }

        int index = str.indexOf(separator);
        if (index == -1) {
            return null;
        }

        String before 	= str.substring(0, index);
        String after 	= str.substring(index + separator.length());

        return new String[] { before, after };
    }

    /**
     * <pre>
     * 将特定字符串加入到字符串数组中（尾部）.
     * 
     * 如果array为null, 则直接字符作为新数组元素返回; 
     * 如果给定的字符串为null, 则直接返回数值.
     * </pre>
     * 
     * @param array 字符串数组, 可以为null, 则将字符作为新数组元素返回.
     * @param strs 需要加入数组的字符串.
     * @return String[] 加入字符串后的字符串数组, 一定不是null.
     */
    public static String[] addArray(String[] array, String... strs) {

        if (null == array || array.length == 0) {
            return strs;
        }

        if (null == strs) {
            return array;
        }

        int newLength = array.length + strs.length;

        String[] _array = new String[newLength];

        System.arraycopy(array, 0, _array, 0, array.length);

        for (int i = 0; i < strs.length; i++) {
            _array[array.length + i] = strs[i];
        }

        return _array;
    }

    /**
     * <pre>
     * 对字符串数组进行排序.
     * 
     * 如果排序的数值array为null, 则直接返回无元素的数组.
     * </pre>
     * 
     * @param array 原始的字符串数组, 可以为null.
     * @return String[] 排序后的字符串数组, 一定不是null.
     */
    public static String[] sortArray(String[] array) {

        if (null == array) {
            return new String[0];
        }

        if (array.length == 0) {
            return array;
        }

        Arrays.sort(array);

        return array;
    }

    /**
     * <pre>
     * 将一个字符串集合对象变换为字符串数组对象.
     * 
     * 如果提供的集合为null, 则直接返回null.
     * </pre>
     * 
     * @param collection 字符串集合对象, 可以为null.
     * @return String[] 字符串数组对象, 有可能为null.
     */
    public static String[] toArray(Collection<String> collection) {

        if (null == collection) {
            return null;
        }

        String[] array = new String[collection.size()];

        return (String[]) collection.toArray(array);
    }

    /**
     * <pre>
     * 清除字符串数组中相同的元素, 同时对元素进行排序（使用TreeSet进行变换）.
     * 
     * 如果字符串数组为null 或 长度==0, 则直接返回该数组.
     * </pre>
     * 
     * @param array 原始的字符串数组.
     * @return String[] 去除重复元素后的字符串数组.
     */
    public static String[] removeDuplicate(String[] array) {

        if (null == array || array.length == 0) {
            return array;
        }

        Set<String> set = new TreeSet<String>();
        for (int i = 0; i < array.length; i++) {
            set.add(array[i]);
        }

        return toArray(set);
    }

    /**
     * <pre>
     * 将给出的字符串用特定的分隔符字符串中的字符拆分成字符串数组. 
     * 使用StringTokenizer类. 
     * trim每个拆分后的结果字符串, 忽略空的拆分结果字符串.
     * </pre>
     * 
     * @param str 给出的字符串.
     * @param separators 分隔符字符集合, 其中的每个字符都是分隔符.
     * @return String[] 拆分后的字符串数组.
     * @see java.util.StringTokenizer
     * @see java.lang.String#trim
     * @see #delimitedListToStringArray
     */
    public static String[] tokenizeToArray(String str, String separators) {
        return tokenizeToArray(str, separators, true, true);
    }

    /**
     * <pre>
     * 将给出的字符串用特定的分隔符字符串中的字符拆分成字符串数组.
     * 使用StringTokenizer类.
     * </pre>
     * 
     * @param str 给出的字符串.
     * @param separators 分隔符字符集合, 其中的每个字符都是分隔符.
     * @param trimTokens 是否需要trim拆分后的结果.
     * @param ignoreEmptyTokens 是否忽略空的拆分结果.
     * @return String[] 拆分后的字符串数组.
     * @see java.util.StringTokenizer
     * @see java.lang.String#trim
     * @see #delimitedListToStringArray
     */
    public static String[] tokenizeToArray(String str, String separators, boolean trimTokens, boolean ignoreEmptyTokens) {

        StringTokenizer st = new StringTokenizer(str, separators);
        List<String> tokens = new ArrayList<String>();

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }

            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }

        return toArray(tokens);
    }

    /**
     * 将以逗号为分隔符的字符串拆分成字符串数组.
     * 
     * @param str 以逗号分隔的字符串, 可以为null.
     * @return String[] 拆分后的字符串数组.
     */
    public static String[] delimitedToArray(String str) {
        return delimitedToArray(str, ",");
    }

    /**
     * <pre>
     * 将给出的字符串用特定的分隔符拆分成字符串数组.
     *  
     * 如果给出的字符串为null, 则返回String[0];
     * 如果分隔符为null, 则返回以源字符串为唯一元素的字符串数组;
     * 如果分隔符为"", 则将源字符串按照每个字符拆分成字符串数组;
     * 否则, 用分隔符拆分源字符串.
     * </pre>
     * 
     * @param str 给出的字符串, 可以为null.
     * @param separator 分隔符可以为null和"".
     * @return 拆分后的字符串数组.
     * @see #tokenizeToArray
     */
    public static String[] delimitedToArray(String str, String separator) {

        if (null == str) {
            return new String[0];
        }

        if (null == separator) {
            return new String[] { str };
        }

        List<String> result = new ArrayList<String>();
        if ("".equals(separator)) {
            for (int i = 0; i < str.length(); i++) {
                result.add(str.substring(i, i + 1));
            }

        } else {
            int index = 0;
            int delIndex = 0;
            while ((delIndex = str.indexOf(separator, index)) != -1) {
                result.add(str.substring(index, delIndex));
                index = delIndex + separator.length();
            }

            if (str.length() > 0 && index <= str.length()) {
                // Add rest of String, but not in case of empty input.
                result.add(str.substring(index));
            }
        }

        return toArray(result);
    }

    /**
     * <pre>
     * 将以逗号为分隔符的字符串拆分成集合对象（Set&lt;String&gt;） 将忽略重复的元素.
     *  
     * 如果给出的字符串为null, 则返回Set[0].
     * </pre>
     * 
     * @param str 以逗号为分隔符的字符串, 可以为null.
     * @return Set&lt;String&gt; 集合对象实例.
     */
    public static Set<String> delimitedToSet(String str) {
        return delimitedToSet(str, ",");
    }

    /**
     * <pre>
     * 将以逗号为分隔符的字符串拆分成集合对象（Set&lt;String&gt;） 将忽略重复的元素.
     *  
     * 如果给出的字符串为null, 则返回Set[0];
     * 如果分隔符为null, 则返回以源字符串为唯一元素的字符串数组;
     * 如果分隔符为"", 则将源字符串按照每个字符拆分成字符串数组;
     * 否则, 用分隔符拆分源字符串.
     * </pre>
     * 
     * @param str 以逗号为分隔符的字符串, 可以为null.
     * @param separator 分隔符可以为null和"".
     * @return Set&lt;String&gt; 集合对象实例.
     */
    public static Set<String> delimitedToSet(String str, String separator) {

        Set<String> set = new TreeSet<String>();
        String[] tokens = delimitedToArray(str, separator);

        for (int i = 0; i < tokens.length; i++) {
            set.add(tokens[i]);
        }

        return set;
    }

    /**
     * 将字符串数组组装成以逗号（","）为分隔符的字符串
     * 
     * @param array 字符串数组
     * @return 组装后的字符串
     */
    public static String toDelimitedString(Object[] array) {
        return toDelimitedString(array, ",");
    }

    /**
     * <pre>
     * 将对象数组组装成以特定分隔符分隔的字符串.使用toString方法获得字符串
     * </pre>
     * 
     * @param arr 对象数组
     * @param separator 分隔符
     * @return String 组装后的字符串
     */
    public static String toDelimitedString(Object[] arr, String separator) {

        if (null == arr) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(separator);
            }
            sb.append(arr[i]);
        }

        return sb.toString();
    }

    /**
     * 将字符串集合对象组装成以逗号（","）为分隔符的字符串
     * 
     * @param coll 字符串集合对象
     * @return 组装后的字符串
     */
    public static String toDelimitedString(Collection<?> coll) {
        return toDelimitedString(coll, ",");
    }

    /**
     * <pre>
     * 将集合对象的每个元素组装成以特定分隔符分隔的字符串.
     * 使用toString方法获得字符串.
     * </pre>
     * 
     * @param coll 集合对象实例, 可以为null.
     * @param separator 分隔符.
     * @return String 组装后的字符串, 一定不是null.
     */
    public static String toDelimitedString(Collection<?> coll, String separator) {
        return toDelimitedString(coll, separator, "", "");
    }

    /**
     * <pre>
     * 将集合对象的每个元素组装成以特定分隔符分隔的字符串,  每个元素可以加上特定的前缀和后缀字符串. 
     * 使用toString方法获得字符串.
     * </pre>
     * 
     * @param coll 集合对象实例, 可以为null.
     * @param separator 分隔符.
     * @param prefix 前缀字符串.
     * @param suffix 后缀字符串.
     * @return String 组装后的字符串, 一定不是null
     */
    public static String toDelimitedString(Collection<?> coll, String separator, String prefix, String suffix) {

        if (null == coll) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        Iterator<?> iterator = coll.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            if (i > 0) {
                sb.append(separator);
            }
            sb.append(prefix).append(iterator.next()).append(suffix);
            i++;
        }

        return sb.toString();
    }

    /**
     * <pre>
     * 将用字符串数组存储的<key-value>表达式变换成Properties对象实例.
     * 
     * 字符串数组中的每个元素都是以特定分隔符隔开的<key-value>表达式, 分隔符左边是关键字, 右边是值.
     * 
     * <pre>
     * 
     * @param array 给出的字符串数组, 可以为null.
     * @param separator <key-value>对的分隔符, 可以为null.
     * @return Properties Properties对象实例, 可以为null.
     */
    public static Properties convertArrayToProperties(String[] array, String separator) {
        return convertArrayToProperties(array, separator, null);
    }

    /**
     * <pre>
     * 将用字符串数组存储的<key-value>表达式变换成Properties对象实例. 
     * 
     * 字符串数组中的每个元素都是以特定分隔符隔开的<key-value>表达式, 分隔符左边是关键字, 右边是值. 
     * 不论是关键字还是值, 在加入Properties对象之前都可以删除给定的字符集合（例如空格）.
     * </pre>
     * 
     * @param array 给出的字符串数组, 可以为null.
     * @param separator <key-value>对的分隔符, 可以为null.
     * @param deleteChars 需要删除的字符集合, 可以为null.
     * @return Properties Properties对象实例, 可以为null.
     */
    public static Properties convertArrayToProperties(String[] array, String separator, String deleteChars) {

        if (null == array || array.length == 0) {
            return null;
        }

        Properties props = new Properties();
        for (int i = 0; i < array.length; i++) {
            String str = array[i];
            if (null != deleteChars) {
                str = deleteChar(array[i], deleteChars);
            }

            String[] _arrs = split(str, separator);
            if (null == _arrs) {
                continue;
            }
            props.setProperty(_arrs[0].trim(), _arrs[1].trim());
        }

        return props;
    }

    // -------------
    // ------------- Convenience methods for working with formatted Strings

    /**
     * <pre>
     * 将给出的字符串以"."进行分解并返回最后部分,
     * 如果给出的字符串为null, 则返回null,
     * 如果给出的字符串种无对应的分割字符, 则直接返回给出的字符.
     * 
     * StringUtil.unqualify("abc.com.cn") = "cn";
     * StringUtil.unqualify("abc,com,cn") = "abc,com,cn";
     * StringUtil.unqualify(null)         = null;
     * </pre>
     * 
     * @param str 给出的字符串.
     * @return String 分解后的最后部分.
     */
    public static String unqualify(String str) {
        return unqualify(str, '.');
    }

    /**
     * <pre>
     * 将给出的字符串指定的字符进行分解并返回最后部分,
     * 如果给出的字符串为null或者分隔字符为空格, 则返回null,
     * 如果给出的字符串种无对应的分割字符, 则直接返回给出的字符.
     * 
     * StringUtil.unqualify("abc.com.cn", ".") = "cn";
     * StringUtil.unqualify("abc.com.cn", null)= null;
     * StringUtil.unqualify("abc.com.cn", ",") = "abc.com.cn";
     * StringUtil.unqualify(null, ".")         = "cn";
     * </pre>
     * 
     * @param str 给出的字符串.
     * @param separator 分隔字符.
     * @return String 分解后的最后部分.
     */
    public static String unqualify(String str, Character separator) {

        if (null == str || null == separator || separator == '\0') {
            return null;
        }

        return str.substring(str.lastIndexOf(separator) + 1);
    }

    /**
     * <pre>
     * 清洗路径字符串中的'..'和'.', 得到完整的路径字符串.
     * 清洗后的路径字符串可以用于有效的比较.
     * 路径字符串中的windows分隔符"\\"将被替换成简单分隔符"/".
     * </pre>
     * 
     * @param path 需要清洗的路径字符串
     * @return String 清洗后的路径字符串
     */
    public static String cleanPath(String path) {

        // 对提供的 path进行清洗, 将 "\\" 全部替换为 "/".
        path = replace(path, FOLDER_SEPARATOR_WINDOWS, FOLDER_SEPARATOR);

        // 获取路径的前缀.
        String prefix = "";

        // 定位路径中的 ":" 的位置.
        int index = path.indexOf(":");
        // 分别截取前缀 和 前缀后面的路径.
        if (index != -1) {
            prefix = path.substring(0, index + 1);
            path = path.substring(index + 1);
        }

        String[] pathArray = delimitedToArray(path, FOLDER_SEPARATOR);
        List<String> pathElements = new LinkedList<String>();
        int tops = 0;

        for (int i = pathArray.length - 1; i >= 0; i--) {
            if (CURRENT_PATH.equals(pathArray[i])) {

            } else if (TOP_PATH.equals(pathArray[i])) {
                tops++;

            } else {
                if (tops > 0) {
                    tops--;

                } else {
                    pathElements.add(0, pathArray[i]);
                }
            }
        }

        for (int i = 0; i < tops; i++) {
            pathElements.add(0, TOP_PATH);
        }

        return prefix + toDelimitedString(pathElements, FOLDER_SEPARATOR);
    }

    /**
     * 比较两个路径字符串是否相等, 比较之前分别清洗两个路径字符串.
     * 
     * @param path1 要比较的第一个路径字符串.
     * @param path2 要比较的第二个路径字符串.
     * @return boolean 两个路径字符串是否相等.
     */
    public static boolean pathEquals(String path1, String path2) {
        return cleanPath(path1).equals(cleanPath(path2));
    }

    /**
     * 在路径字符串中添加相对路径.
     * 
     * @param path 路径字符串.
     * @param relativePath 要添加的相对路径.
     * @return String 添加相对路径后的路径字符串, 有可能为null.
     */
    public static String attachRelativePath(String path, String relativePath) {

        if (null == path || null == relativePath) {
            return null;
        }

        int index = path.lastIndexOf(FOLDER_SEPARATOR);

        if (index != -1) {
            String newPath = path.substring(0, index);
            if (!relativePath.startsWith(FOLDER_SEPARATOR)) {
                newPath += FOLDER_SEPARATOR;
            }
            return newPath + relativePath;

        } else {
            return relativePath;
        }
    }

    /**
     * <pre>
     * 从路径字符串中分解出路径名称部分.
     * 
     * 例如："D:/mypath/myfile.txt" -> "D:/mypath"
     * 
     * <pre>
     * 
     * @param path 路径字符串, 可以为null.
     * @return String 分解出的路径名称部分, 有可能为null.
     */
    public static String getPath(String path) {

        if (null == path) {
            return null;
        }

        int index = path.lastIndexOf(FOLDER_SEPARATOR);

        return (index != -1 ? path.substring(0, index) : path);
    }

    /**
     * <pre>
     * 从路径字符串中分解出文件名称的名称部分.
     * 
     * 例如："D:/mypath/myfile.txt" -> "myfile"
     * 
     * <pre>
     * 
     * @param path 路径字符串, 可以为null.
     * @return String 分解出的文件名称部分, 有可能为null.
     */
    public static String getFilename(String path) {

        if (null == path) {
            return null;
        }

        int start = path.lastIndexOf(FOLDER_SEPARATOR);

        String filename = (start != -1 ? path.substring(start + 1) : path);

        int end = filename.lastIndexOf(EXTENSION_SEPARATOR);

        return (end != -1 ? filename.substring(0, end) : filename);
    }

    /**
     * <pre>
     * 从路径字符串中分解出文件名称部分.
     * 
     * 例如："D:/mypath/myfile.txt" -> "myfile.txt"
     * 
     * <pre>
     * 
     * @param path 路径字符串, 可以为null.
     * @return String 分解出的文件名称部分, 有可能为null.
     */
    public static String getFileFullname(String path) {

        if (null == path) {
            return null;
        }

        int index = path.lastIndexOf(FOLDER_SEPARATOR);

        return (index != -1 ? path.substring(index + 1) : path);
    }

    /**
     * <pre>
     * 从路径字符串中分解出文件后缀部分.
     * 
     * 例如："D:/mypath/myfile.txt" -> "txt"
     * 
     * <pre>
     * 
     * @param path 路径字符串, 可以为null.
     * @return String 分解出的文件后缀部分, 有可能为null和"".
     */
    public static String getFileExt(String path) {

        if (null == path) {
            return null;
        }

        int index = path.lastIndexOf(EXTENSION_SEPARATOR);

        return (index != -1 ? path.substring(index + 1) : "");
    }

    /**
     * <pre>
     * 去除路径字符串中的文件后缀部分.
     * 
     * 例如："D:/mypath/myfile.txt" -> "D:/mypath/myfile"
     * 
     * <pre>
     * 
     * @param path 路径字符串, 可以为null.
     * @return String 去除文件后缀部分后的字符串, 有可能为null.
     */
    public static String stripExt(String path) {

        if (null == path) {
            return null;
        }

        int index = path.lastIndexOf(EXTENSION_SEPARATOR);

        return (index != -1 ? path.substring(0, index) : path);
    }

    /**
     * 确保字符串的长度.
     * 
     * @param str 原字符串.
     * @param len 验证的长度.
     * @return String
     */
    public static String keepLen(String str, int len) {
        return keepLen(str, len, "0", true);
    }

    /**
     * 确保字符串的长度.
     * 
     * @param str 原字符串.
     * @param len 验证的长度.
     * @param ch 添加的字符.
     * @return String
     */
    public static String keepLen(String str, int len, String ch) {
        return keepLen(str, len, ch, true);
    }

    /**
     * 确保字符串的长度.
     * 
     * @param str 原字符串.
     * @param len 验证的长度.
     * @param ch 添加的字符.
     * @param isBefore 字符前部追加.
     * @return String
     */
    public static String keepLen(String str, int len, String ch, boolean isBefore) {

        if (str.length() >= len) {
            return str;
        }

        StringBuffer sb = new StringBuffer(str);
        if (isBefore) {
            while (sb.length() < len) {
                sb.insert(0, ch);
            }

        } else {
            while (sb.length() < len) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * 清理给定字符串中的中文汉字.
     * 
     * @param str 需要清理的字符串.
     * @return String
     */
    public static String cleanZH_CN(String str) {
        if (isBlank(str)) {
            return str;
        }
        Pattern cleanPattern 	= Pattern.compile("[\u4e00-\u9fa5]");
        Matcher cleaner 		= cleanPattern.matcher(str);
        str 					= cleaner.replaceAll("");
        return str;
    }

    /**
     * 清理给定字符串中的换行、制表等特殊格式符号.
     * 
     * @param str 需要清理的字符串.
     * @return String
     */
    public static String cleanFormat(String str) {
        if (isBlank(str)) {
            return str;
        }
        Pattern cleanPattern 	= Pattern.compile("\\s*|\t|\r|\n");
        Matcher cleaner 		= cleanPattern.matcher(str);
        str = cleaner.replaceAll("");
        return str;
    }

    /**
     * 随机生成指定长度的字符串
     * @param length
     * @return
     */
    public static String randomString(int length){
    	if(length <= 0){
    		return "";
    	}
    	int random_char_length	= RANDOM_CHAR_SEEDS.length;
    	Random random			= new Random();
    	int first_char			= random.nextInt(random_char_length);
    	StringBuilder sb		= new StringBuilder(first_char);
    	
    	int random_length		= RANDOM_CHAR_SEEDS.length + RANDOM_NUMBER_SEEDS.length;
    	for(int i=1;i<length;i++){
    		int index			= random.nextInt(random_length);
    		if(index >= random_char_length){
    			sb.append(RANDOM_NUMBER_SEEDS[index - random_char_length]);
    		}else{
    			sb.append(RANDOM_CHAR_SEEDS[index]);
    		}
    	}
    	return sb.toString();
    }
    
    /**
     * 随机数字	
     * @param length 只能是1-9
     * @return
     */
    public static int randomInt(int length){
    	if(length < 1 || length > 9){
    		throw new QunaRuntimeException("length is between 1-9!");
    	}
    	int random_length	= RANDOM_NUMBER_SEEDS.length;
    	int pos				= 0;
    	Random random		= new Random();
    	StringBuilder sb	= new StringBuilder();
    	while(pos < length){
    		int curr		= random.nextInt(random_length);
    		char ch			= RANDOM_NUMBER_SEEDS[curr];
    		if(ch == '0'){
    			continue;
    		}
    		sb.append(ch);
    		pos ++;
    	}
    	return Integer.parseInt(sb.toString());
    }
    
    
    public static String randomOnlyChar(int length){
    	if(length <= 0){
    		return "";
    	}
    	int random_length	= RANDOM_CHAR_SEEDS.length;
    	int pos				= 0;
    	Random random		= new Random();
    	StringBuilder sb	= new StringBuilder();
    	while(pos < length){
    		int curr		= random.nextInt(random_length);
    		char ch			= RANDOM_CHAR_SEEDS[curr];
    		sb.append(ch);
    		pos ++;
    	}
    	return sb.toString();
    }
    
    public static void main(String[] args) {
        System.out.println(StringUtils.cleanFormat("重庆 \r\nt\tT2\r\\t(出发);\r\n\tt昆明\r\\n\t (到达),\rtt有,\r\t无\r\nt,73G"));
        System.out.println(StringUtils.cleanZH_CN("重庆 \r\nt\tT2\r\\t(出发);\r\n\tt昆明\r\\n\t (到达),\rtt有,\r\t无\r\nt,73G"));
        System.out.println(String.valueOf(Character.isWhitespace(' ')));
        System.out.println(randomString(32));
    }
}
