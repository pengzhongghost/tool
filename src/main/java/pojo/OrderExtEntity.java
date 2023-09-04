package pojo;


import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhubo
 * @since 2022/12/26 16:42
 */
public class OrderExtEntity {

    /**
     * 扩展表id
     */
    private Long id;

    /**
     * 订单表主键Id
     */
    private Long orderId;

    /**
     * 买家名称
     */
    private String buyerName;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 第三方平台商品名称
     */
    private String platformProductName;

    /**
     * 第三方平台商品图片
     */
    private String platformProductImg;

    /**
     * 机构名称
     */
    private String institutionName;

    /**
     * 二级团长名称
     */
    private String secondInstitutionName;

    /**
     * App名称（抖音，火山）
     */
    private String appName;

    /**
     * 抖音 团长订单类型：0-普通；1-一级团长；2-二级团长
     */
    private Integer colonelType;

    /**
     * 百应id
     */
    private String authorBuyinId;

    /**
     * 下单体裁：shop_list 橱窗，video 视频，live 直播，others 其他
     */
    private String mediaType;

    /**
     * 体裁 id (只返回视频/直播间 id)
     */
    private Long mediaId;

    /**
     * 订单发货状态1-已发货，0-未发货
     */
    private Integer deliveryStatus;

    /**
     * 备注（渠道）
     */
    private String channelRemark;

    /**
     * 备注（招商）
     */
    private String partnerRemark;

    /**
     * 历史团长id
     */
    private Integer hiPartnerid;

    /**
     * 招商大区id
     */
    private Integer partnerTeamId;

    /**
     * 招商分部id
     */
    private Integer partnerBranchId;

    /**
     * 招商小组id
     */
    private Integer partnerGroupId;

    /**
     * 历史渠道id
     */
    private Integer hiChannelid;

    /**
     * 渠道大区id
     */
    private Integer channelTeamId;

    /**
     * 渠道分部id
     */
    private Integer channelBranchId;

    /**
     * 渠道小组id
     */
    private Integer channelGroupId;

    /**
     * 用户运营id
     */
    private Integer hiMchannelId;

    /**
     * 用户运营大区id
     */
    private Integer userMchannelTeamId;

    /**
     * 用户运营分部id
     */
    private Integer userMchannelBranchId;

    /**
     * 用户运营小组id
     */
    private Integer userMchannelGroupId;

    /**
     * 业绩类型
     */
    private Integer achievementType;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 订单修改时间(外部平台输入)
     */
    private Date orderUpdateTime;

    /**
     * PID
     */
    private String pid;

    /**
     * 自定义参数
     */
    private String externalInfo;

    /**
     * 分销类型，Live：直播
     */
    private String mediaTypeName;

    /**
     * 渠道实际佣金
     */
    private BigDecimal channelRealCommission;

    /**
     * 渠道预估佣金
     */
    private BigDecimal channelEstimatedCommission;

    /**
     * 渠道推广费率
     */
    private BigDecimal channelCommissionRate;

    /**
     * 是否托管
     */
    private Boolean isTrusteeship;

    /**
     * 分销类型
     */
    private String goodShareType;

    /**
     * 团长百应ID
     */
    private String colonelBuyinId;

    /**
     * 二级团长百应ID
     */
    private String secondColonelBuyinId;

    /**
     * 快赚客id
     */
    private Long kwaimoneyUserId;

    /**
     * 快赚客昵称
     */
    private String kwaimoneyUserNickName;

    /**
     * 渠道分成比例
     */
    private BigDecimal channelSplitRate;
    /**
     * 买家openid
     */
    private String buyerOpenId;
    /**
     * 渠道分成比例
     */
    private BigDecimal talentSplitRate;
    /**
     * 预估技术服务费
     */
    private BigDecimal techEstimatedServiceFee;
    /**
     * 商家实际佣金支出
     */
    private BigDecimal shopRealCommission;
    /**
     * 商家预估佣金支出
     */
    private BigDecimal shopEstimatedCommission;
    /**
     * 预估机构佣金收入
     */
    private BigDecimal institutionEstimatedCommission;
    /**
     * 机构实际佣金收入
     */
    private BigDecimal institutionRealCommission;
    /**
     * 机构预估奖励佣金收入
     */
    private BigDecimal institutionEstimatedSteppedCommission;
    /**
     * 机构结算奖励佣金收入，
     */
    private BigDecimal institutionSettleSteppedCommission;
    /**
     * 支付补贴）
     */
    private BigDecimal paySubsidy;
    /**
     * 平台补贴
     */
    private BigDecimal platformSubsidy;
    /**
     * 达人补贴
     */
    private BigDecimal talentSubsidy;

    /**
     * 达人预估奖励佣金收入
     */
    private BigDecimal talentEstimatedSteppedCommission;

    /**
     * 达人结算奖励佣金收入
     */
    private BigDecimal talentSettleSteppedCommission;

    /**
     * 是否阶梯佣金
     */
    private Boolean isSteppedPlan;

    /**
     * 总佣金（预估），对应百应订单明细中的总佣金
     */
    private BigDecimal estimatedTotalCommission;

    /**
     * 推广者类型
     */
    private Integer promotionType;

    /**
     * 分成比例
     */
    private BigDecimal shareRate;

    /**
     * 奖励服务费收入
     */
    private BigDecimal rewardServiceInCome;

    /**
     * 接单服务费收入
     */
    private BigDecimal orderTakingServiceIncome;

    /**
     * 资金流转类型
     */
    private Integer fundType;

    /**
     * 贷款基数
     */
    private BigDecimal loanBase;

    /**
     * 商品名字
     */
    private String productName;

    /**
     * 商品单价
     */
    private BigDecimal productPrice;

    /**
     * skuId
     */
    private Long skuId;

    /**
     * 订单发货状态1-已发货，0-未发货
     */
    private Integer sendStatus;

    /**
     * 二级团长预估技术服务费
     */
    private BigDecimal secTechEstimatedServiceFee;

    /**
     * 二级团长技术服务费
     */
    private BigDecimal secTechServiceFee;

    /**
     * 二级团长技术服务费率
     */
    private BigDecimal secTechServiceFeeRate;

    /**
     * 二级团长结算技术服务费
     */
    private BigDecimal secTechSettledServiceFee;

    /**
     * 达人结算佣金收入
     */
    private BigDecimal talentSettledCommission;

    /**
     * 平台订单详情id
     */
    private String platformOrderItemId;

    /**
     * 星选商品一级类目ID
     */
    private Integer xxProductCategoryId;
    /**
     * 热度抖客百应id
     */
    private String reduDoukeBuyinId;

    /**
     * 独立抖客昵称
     */
    private String reduDoukeBuyinName;

    /**
     * 分销活动Id，1000-超值购 1001-秒杀（活动页推广）
     */
    private Long adsActivityId;

    /**
     * 匹配业务信息方式
     */
    private Integer matchedBy;

    /**
     * 总预估服务费
     */
    private BigDecimal totalServiceIncome;

    /**
     * 总结算服务费
     */
    private BigDecimal totalRealServiceIncome;

    /**
     * 二级团服务费率（订单一二级团长都是热度的场景用）
     */
    private BigDecimal serviceRateExt;

    /**
     * 二级团预估服务费收入（订单一二级团长都是热度的场景用）
     */
    private BigDecimal estimateServiceIncomeExt;

    /**
     * 二级团实际服务费收入（订单一二级团长都是热度的场景用）
     */
    private BigDecimal realServiceIncomeExt;

    /**
     * 星选订单类型.1:只领样,2:只使用添加橱窗功能,3:使用小程序领样并使用添加橱窗功能(用于小程序)
     */
    private Integer xxOrderType;

    /**
     * 选品App client_key
     */
    private String pickSourceClientKey;

    /**
     * 是否奖励订单
     */
    private Boolean isRewardOrder;

    /**
     * 奖励类型
     */
    private String rewardType;

    /**
     * php定义的redu达人唯一id(php特有)
     */
    private Integer memberTalentId;

    /**
     * 小程序用户id(php特有)
     */
    public Integer memberId;

    /**
     * 作者抖音抖店open_id
     */
    private String douDianAuthorOpenid;

    /**
     * 其他团长id
     */
    private Long otherInstitutionId;

    /**
     * 其他团长id名称
     */
    private String otherInstitutionName;


    /**
     * 抖音二级团长订单中的一级团长百应ID
     */
    private String otherColonelBuyinId;
}