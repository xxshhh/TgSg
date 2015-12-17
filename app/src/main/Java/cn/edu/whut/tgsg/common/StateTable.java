package cn.edu.whut.tgsg.common;

/**
 * 状态对照表
 * <p/>
 * Created by xwh on 2015/12/17.
 */
public class StateTable {

    /**
     * 获取编辑下拉框内容
     *
     * @return
     */
    public static String[] getEditorSpinner() {
        String[] array = new String[]{"编辑初审", "待专家审核", "专家审核", "编辑复审", "通过", "录用"};
        return array;
    }

    /**
     * 获取专家下拉框内容
     *
     * @return
     */
    public static String[] getExpertSpinner() {
        String[] array = new String[]{"通过", "不通过"};
        return array;
    }

    /**
     * 获取状态字符串
     *
     * @param state
     * @return
     */
    public static String getString(int state) {
        String text;
        switch (state) {
            case 1:
                text = "未受理";
                break;
            case 2:
                text = "编辑初审";
                break;
            case 3:
                text = "待专家审核";
                break;
            case 4:
                text = "专家审核";
                break;
            case 5:
                text = "编辑复审";
                break;
            case 6:
                text = "通过";
                break;
            case 7:
                text = "录用";
                break;
            case 8:
                text = "取消投稿";
                break;
            case 9:
                text = "未通过";
                break;
            default:
                text = "错误状态";
        }
        return text;
    }
}
