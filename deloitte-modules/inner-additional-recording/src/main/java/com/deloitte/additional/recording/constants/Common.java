package com.deloitte.additional.recording.constants;

/**
 * 常量类
 *
 * @author 冉浩岑
 * @date 2022/11/7 16:38
*/
public enum Common {

    /** 通用信息 */
    NULL;
    /** 默认页码 */
    public static final Integer DEFAUT_PAGE_NUM = 1;
    /** 默认页面size */
    public static final Integer DEFAUT_PAGE_SIZE = 10;
    /** 可查年份 */
    public static final String SEARCH_YEAR = "search_year";
    /** 数据来源 */
    public static final String DATA_SOURCE = "data_source";
    /** 显示类型 */
    public static final String SHOW_TYPE = "show_type";
    /** 限定查询一条 */
    public static final String SQL_LIMIT_ONE = " limit 1";
    /** 操作成功 */
    public static final String DO_SUCCESS = "操作成功";
    /** 新增成功 */
    public static final String INSERT_SUCCESS = "新增成功";
    /** 修改成功 */
    public static final String UPDATE_SUCCESS = "修改成功";
    /** 新增失败 */
    public static final String INSERT_FAIL = "新增失败";
    /** 敞口指标重复 */
    public static final String REPEAT_MAS_EVD = "敞口指标重复";
    /** 请传入参数 */
    public static final String PLEASE_SEND_PARAM = "请传入参数";
    /** 正常 */
    public static final Integer NORMAL = 1;
    /** 删除 */
    public static final Integer DELETE = 0;
    /** 中心库数据库表名（缺前缀） */
    public static final String MODEL_MASTER = "_model_master";

    /** 中心库数据库表名（缺前缀） */
    public static final String SYS_DICT_TYPE_COLLSTAT = "collstat";

    /** sys_role角色Id为补录人员  */
    public static final Integer SYS_ROLE_ID_COLLOCTER = 2;
    /** sys_role角色Id为审核人员  */
    public static final Integer SYS_ROLE_ID_APPROVER = 3;
    /** qualCode---front */
    public static final String QUAL_CODE_FRONT = "Q_";
    /** master---front */
    public static final String MASTER_FRONT = "M_";
    /** EVD---front */
    public static final String EVD_FRONT = "E_";


    //mq相关
    public static final String CONSUMER_GROUP ="group1";

    public static final String UPDATE_EVD_BATCH_TOPIC ="updateEvdBatch";

    public static final String UPDATE_QUAL_BATCH_TOPIC ="updateEvdBatch";

    public static final String BASE_FIN_DATA_RECORDING_TOPIC ="baseFinDataRecording";

    public static final String STRUCTURED_NOTES_TOPIC ="structuredNotes";

    //sysData
    public static final String DICTTYPE_REPORTTYPE ="reportType";
    public static final String DICTTYPE_RDATAFREQUENCY ="dataFrequency";
    public static final String DICTTYPE_SEARCH_YEAR ="search_year";
    public static final String DICTTYPE_COLLSTAT ="collStat";

    //sysConf
    public static final String SYS_CONF__NAME_IMGPATH ="imgPath";

    /**
     * {@link com.deloitte.common.core.utils.file.MimeTypeUtils#IMAGE_EXTENSION}
     */
    public static final String[] DEFAULT_IMAGES_EXTENSION = {"bmp", "gif", "jpg", "jpeg", "png",};


    //缓存相关
    /**
     * 拉取crm主体任务
     * {
     *     total:100,
     *     success:10,
     *     fail:10
     * }
     */
    public static final String CRM_PULL_TASK ="crm_pull_task";


}
