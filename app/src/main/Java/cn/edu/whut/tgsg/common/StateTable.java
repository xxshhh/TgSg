package cn.edu.whut.tgsg.common;

/**
 * 状态对照表
 * <p/>
 * Created by xwh on 2015/12/17.
 */
public class StateTable {

    /**
     * 获取个人信息界面学历单选按钮内容
     *
     * @return
     */
    public static String[] getPersonDegreeRadio() {
        String[] array = new String[]{"小学", "初中", "高中", "中专", "大学", "研究生"};
        return array;
    }

    /**
     * 获取个人信息界面专业单选按钮内容
     *
     * @return
     */
    public static String[] getPersonMajorRadio() {
        String[] array = new String[]{"计算机", "经济学", "法学", "电子信息", "材料化学", "农学", "医学"};
        return array;
    }

    /**
     * 获取个人信息界面研究方向单选按钮内容
     *
     * @return
     */
    public static String[] getPersonResearchRadio() {
        String[] array = new String[]{"计算机", "经济学", "法学", "电子信息", "材料化学", "农学", "医学"};
        return array;
    }

    /**
     * 获取编辑稿件状态spinner内容
     *
     * @return
     */
    public static String[] getEditorStateSpinner() {
        String[] array = new String[]{"编辑初审中", "待分配专家", "专家审核中", "编辑复审中", "已通过", "已录用"};
        return array;
    }

    /**
     * 获取编辑初审结果spinner内容
     *
     * @return
     */
    public static String[] getEditorExamineSpinner() {
        String[] array = new String[]{"通过", "不通过"};
        return array;
    }

    /**
     * 获取编辑复审结果spinner内容
     *
     * @return
     */
    public static String[] getEditorReExamineSpinner() {
        String[] array = new String[]{"通过", "更换专家复审", "不通过"};
        return array;
    }

    /**
     * 获取专家审稿结果spinner内容
     *
     * @return
     */
    public static String[] getExpertExamineSpinner() {
        String[] array = new String[]{"通过", "不通过"};
        return array;
    }

    /**
     * 获取分配专家的spinner内容
     *
     * @return
     */
    public static String[] getDistributeExpertSpinner() {
        String[] array = new String[]{"全部", "研究方向", "专业", "姓名"};
        return array;
    }

    /**
     * 获取分配专家的spinner内容(English)
     *
     * @return
     */
    public static String[] getDistributeExpertEngSpinner() {
        String[] array = new String[]{"all", "research", "professional", "name"};
        return array;
    }

    /**
     * 获取类别的spinner内容
     *
     * @return
     */
    public static String[] getTypeSpinner() {
        String[] array = new String[]{"软件工程", "网络", "数据库", "程序设计", "计算机系统", "计算机编程"};
        return array;
    }

    /**
     * 获取审稿表result内容
     *
     * @param result
     * @return
     */
    public static String getResultString(int result) {
        String text;
        switch (result) {
            case 0:
                text = "受理";
                break;
            case 1:
                text = "编辑一审未通过";
                break;
            case 2:
                text = "编辑一审通过";
                break;
            case 3:
                text = "编辑复审未通过";
                break;
            case 4:
                text = "编辑复审通过";
                break;
            case 5:
                text = "需专家复审";
                break;
            case 6:
                text = "专家审核通过";
                break;
            case 7:
                text = "专家审核未通过";
                break;
            case 8:
                text = "录用";
                break;
            default:
                text = "错误状态";
        }
        return text;
    }

    /**
     * 获取状态state字符串
     *
     * @param state
     * @return
     */
    public static String getStateString(int state) {
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
                text = "未通过";
                break;
            case 8:
                text = "录用";
                break;
            case 9:
                text = "取消投稿";
                break;
            default:
                text = "错误状态";
        }
        return text;
    }
}
