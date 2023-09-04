package tool;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.select.Select;
import pojo.OrderExtEntity;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author pengzhong
 * @since 2023/6/19
 */
public class HiveSqlParseTest {

    private static final String sql = "CREATE TABLE `redu_order` (\n" +
            "  `paid_time` datetime NOT NULL COMMENT \"付款时间\",\n" +
            "  `platform_order_id` bigint(20) NOT NULL COMMENT \"第三方平台订单id\",\n" +
            "  `platform_type` tinyint(4) NOT NULL DEFAULT \"1\" COMMENT \"第三方平台类型（1、抖音；2、快手）\",\n" +
            "  `platform_product_id` bigint(20) NOT NULL COMMENT \"第三方平台商品id\",\n" +
            "  `order_status` tinyint(4) NOT NULL COMMENT \"订单状态（1、已付款；2、已收货；3、已结算；4、已退款）\",\n" +
            "  `applet_peg` varchar(50) NULL DEFAULT \"0\" COMMENT \"小程序标记，从小程序同步过来的会标记\",\n" +
            "  `second_institution_peg` tinyint(4) NULL COMMENT \"二级团长标记\",\n" +
            "  `id` varchar(128) NOT NULL COMMENT \"订单id（分布式id）\",\n" +
            "  `settlement_time` datetime NULL COMMENT \"结算时间\",\n" +
            "  `refund_time` datetime NULL COMMENT \"退款时间\",\n" +
            "  `delivery_time` datetime NULL COMMENT \"发货时间\",\n" +
            "  `recv_time` datetime NULL COMMENT \"收货时间\",\n" +
            "  `platform_update_time` datetime NULL COMMENT \"第三方平台数据更新时间\",\n" +
            "  `create_time` datetime NOT NULL COMMENT \"创建时间\",\n" +
            "  `update_time` datetime NOT NULL COMMENT \"修改时间\",\n" +
            "  `creator_id` int(11) NULL COMMENT \"创建人\",\n" +
            "  `modifier_id` int(11) NULL COMMENT \"修改人\",\n" +
            "  `is_delete` tinyint(4) NULL COMMENT \"是否删除\",\n" +
            "  `institution_id` bigint(20) NULL COMMENT \"机构id\",\n" +
            "  `second_institution_id` bigint(20) NULL COMMENT \"二级机构id\",\n" +
            "  `buy_count` int(11) NULL COMMENT \"商品数量\",\n" +
            "  `product_id` bigint(20) NULL COMMENT \"热度商品id\",\n" +
            "  `activity_id` bigint(20) NULL COMMENT \"活动id\",\n" +
            "  `second_activity_id` bigint(20) NULL COMMENT \"二级活动id\",\n" +
            "  `pay_amount` decimal64(10, 3) NULL COMMENT \"支付金额\",\n" +
            "  `estimate_settlement_amount` decimal64(10, 3) NULL COMMENT \"预估参与结算金额\",\n" +
            "  `settlement_amount` decimal64(10, 3) NULL COMMENT \"结算金额\",\n" +
            "  `service_rate` decimal64(10, 3) NULL COMMENT \"服务费率\",\n" +
            "  `estimate_service_income` decimal64(10, 3) NULL COMMENT \"预估服务费收入\",\n" +
            "  `real_service_income` decimal64(10, 3) NULL COMMENT \"实际服务费收入\",\n" +
            "  `second_real_commission` decimal64(10, 3) NULL COMMENT \"二级团长手续费\",\n" +
            "  `second_estimated_commission` decimal64(10, 3) NULL COMMENT \"二级团长预估服务费\",\n" +
            "  `second_commission_rate` decimal64(10, 3) NULL COMMENT \"二级团长预估服务费率\",\n" +
            "  `tech_service_fee` decimal64(10, 3) NULL COMMENT \"技术服务费\",\n" +
            "  `tech_service_fee_rate` decimal64(10, 3) NULL COMMENT \"技术服务费率\",\n" +
            "  `settled_tech_service_fee` decimal64(10, 3) NULL COMMENT \"结算技术服务费\",\n" +
            "  `system_multiple` smallint(6) NULL COMMENT \"系统倍数\",\n" +
            "  `weight_multiple` decimal64(10, 3) NULL COMMENT \"权重倍数\",\n" +
            "  `achievements_order_multiple` decimal64(10, 3) NULL COMMENT \"业绩订单倍数\",\n" +
            "  `is_gen_order_multiple` tinyint(4) NULL COMMENT \"是否已生成订单倍数\",\n" +
            "  `shop_id` bigint(20) NULL COMMENT \"店铺id\",\n" +
            "  `platform_shop_id` bigint(20) NULL COMMENT \"第三方平台店铺id\",\n" +
            "  `buyer_id` int(11) NULL COMMENT \"买家id\",\n" +
            "  `channel_talent_id` int(11) NULL COMMENT \"渠道达人id\",\n" +
            "  `nickname` varchar(50) NULL COMMENT \"达人昵称\",\n" +
            "  `shortid` varchar(50) NULL COMMENT \"达人抖音/火山号\",\n" +
            "  `partner_id` int(11) NULL COMMENT \"所属招商id\",\n" +
            "  `partner_dept_id` int(11) NULL COMMENT \"部门id\",\n" +
            "  `partner_dept_id_path` varchar(255) NULL COMMENT \"招商部门path\",\n" +
            "  `partner_dept_name_path` varchar(500) NULL COMMENT \"招商部门名称path\",\n" +
            "  `channel_id` int(11) NULL COMMENT \"所属渠道id\",\n" +
            "  `channel_dept_id` int(11) NULL COMMENT \"所属渠道部门id\",\n" +
            "  `channel_dept_id_path` varchar(255) NULL COMMENT \"渠道部门path\",\n" +
            "  `channel_dept_name_path` varchar(500) NULL COMMENT \"渠道部门名称path\",\n" +
            "  `user_carrier_id` int(11) NULL COMMENT \"所属用户运营\",\n" +
            "  `buyer_name` varchar(50) NULL COMMENT \"买家名称\",\n" +
            "  `shop_name` varchar(50) NULL COMMENT \"店铺名称\",\n" +
            "  `platform_product_name` varchar(255) NULL COMMENT \"第三方平台商品名称\",\n" +
            "  `platform_product_img` varchar(255) NULL COMMENT \"第三方平台商品图片\",\n" +
            "  `institution_name` varchar(50) NULL COMMENT \"机构名称\",\n" +
            "  `second_institution_name` varchar(50) NULL COMMENT \"二级团长名称\",\n" +
            "  `author_open_id` varchar(50) NULL COMMENT \"作者抖音open_id\",\n" +
            "  `app_name` varchar(25) NULL COMMENT \"App名称（抖音，火山）\",\n" +
            "  `colonel_type` tinyint(4) NULL COMMENT \"团长订单类型：0-普通；1-一级团长；2-二级团长\",\n" +
            "  `author_buyin_id` varchar(50) NULL COMMENT \"百应id\",\n" +
            "  `media_type` varchar(25) NULL COMMENT \"下单体裁：shop_list 橱窗，video 视频，live 直播，others 其他\",\n" +
            "  `media_id` bigint(20) NULL COMMENT \"体裁 id (只返回视频/直播间 id)\",\n" +
            "  `delivery_status` tinyint(4) NULL COMMENT \"订单发货状态1-已发货，0-未发货\",\n" +
            "  `channel_remark` varchar(64) NULL COMMENT \"备注（渠道）\",\n" +
            "  `partner_remark` varchar(64) NULL COMMENT \"备注（招商\",\n" +
            "  `final_service_rate` decimal64(10, 3) NULL COMMENT \"最终服务费率\",\n" +
            "  `final_service_income` decimal64(10, 3) NULL COMMENT \"最终服务费\",\n" +
            "  `order_data_type` int(11) NULL COMMENT \"数据类型\",\n" +
            "  `talent_estimated_commission` decimal64(10, 3) NULL COMMENT \"达人预估佣金收入\",\n" +
            "  `talent_real_commission` decimal64(10, 3) NULL COMMENT \"达人实际佣金收入\",\n" +
            "  `talent_commission_rate` decimal64(10, 3) NULL COMMENT \"达人佣金率\",\n" +
            "  `uid` varchar(50) NULL COMMENT \"抖音达人uid\",\n" +
            "  `hi_partnerId` int(11) NULL COMMENT \"历史团长id\",\n" +
            "  `partner_team_id` int(11) NULL COMMENT \"招商大区id\",\n" +
            "  `partner_branch_id` int(11) NULL COMMENT \"招商分部id\",\n" +
            "  `partner_group_id` int(11) NULL COMMENT \"招商小组id\",\n" +
            "  `hi_channelId` int(11) NULL COMMENT \"历史渠道id\",\n" +
            "  `channel_team_id` int(11) NULL COMMENT \"渠道大区id\",\n" +
            "  `channel_branch_id` int(11) NULL COMMENT \"渠道分部id\",\n" +
            "  `channel_group_id` int(11) NULL COMMENT \"渠道小组id\",\n" +
            "  `hi_mchannel_id` int(11) NULL COMMENT \"用户运营id\",\n" +
            "  `user_mchannel_team_id` int(11) NULL COMMENT \"用户运营大区id\",\n" +
            "  `user_mchannel_branch_id` int(11) NULL COMMENT \"用户运营分部id\",\n" +
            "  `user_mchannel_group_id` int(11) NULL COMMENT \"用户运营小组id\",\n" +
            "  `achievement_type` int(11) NULL COMMENT \"用户运营小组id\",\n" +
            "  `channel_estimated_commission` decimal64(10, 3) NULL COMMENT \"渠道预估佣金收入\",\n" +
            "  `channel_real_commission` decimal64(10, 3) NULL COMMENT \"渠道实际佣金收入\",\n" +
            "  `channel_commission_rate` decimal64(10, 3) NULL COMMENT \"渠道佣金率\",\n" +
            "  `is_trusteeship` tinyint(4) NULL COMMENT \"是否托管\",\n" +
            "  `good_share_type` varchar(64) NULL COMMENT \"分销类型。ProductDetail：商品分销；Live：直播分销；Activity：频道活动；Mix：营销活动\",\n" +
            "  `colonel_buyin_id` varchar(64) NULL COMMENT \"团长百应ID\",\n" +
            "  `second_colonel_buyin_id` varchar(64) NULL COMMENT \"二级团长百应ID\",\n" +
            "  `kwaimoney_user_id` bigint(20) NULL COMMENT \"快赚客id\",\n" +
            "  `kwaimoney_user_nick_name` varchar(64) NULL COMMENT \"快赚客昵称\",\n" +
            "  `channel_split_rate` decimal64(10, 3) NULL COMMENT \"渠道分成比例\",\n" +
            "  `buyer_open_id` varchar(256) NULL COMMENT \"买家open id\",\n" +
            "  `talent_split_rate` decimal64(10, 3) NULL COMMENT \"渠道分成比例\",\n" +
            "  `tech_estimated_service_fee` decimal64(10, 3) NULL COMMENT \"预估技术服务费\",\n" +
            "  `shop_real_commission` decimal64(10, 3) NULL COMMENT \"商家实际佣金支出\",\n" +
            "  `shop_estimated_commission` decimal64(10, 3) NULL COMMENT \"商家预估佣金支出\",\n" +
            "  `institution_estimated_commission` decimal64(10, 3) NULL COMMENT \"预估机构佣金收入\",\n" +
            "  `institution_real_commission` decimal64(10, 3) NULL COMMENT \"机构实际佣金收入\",\n" +
            "  `institution_estimated_stepped_commission` decimal64(10, 3) NULL COMMENT \"机构预估奖励佣金收入\",\n" +
            "  `institution_settle_stepped_commission` decimal64(10, 3) NULL COMMENT \"机构结算奖励佣金收入\",\n" +
            "  `pay_subsidy` decimal64(10, 3) NULL COMMENT \"支付补贴\",\n" +
            "  `platform_subsidy` decimal64(10, 3) NULL COMMENT \"平台补贴\",\n" +
            "  `talent_subsidy` decimal64(10, 3) NULL COMMENT \"达人补贴\",\n" +
            "  `talent_estimated_stepped_commission` decimal64(10, 3) NULL COMMENT \"达人预估奖励佣金收入\",\n" +
            "  `talent_settle_stepped_commission` decimal64(10, 3) NULL COMMENT \"达人结算奖、、、励佣金收入\",\n" +
            "  `is_stepped_plan` tinyint(4) NULL COMMENT \"是否阶梯佣金\",\n" +
            "  `estimated_total_commission` decimal64(10, 3) NULL COMMENT \"总佣金（预估），对应百应订单明细中的总佣金\",\n" +
            "  `promotion_type` tinyint(4) NULL COMMENT \"推广者类型 0-无，1-达人推广，2-团长推广\",\n" +
            "  `share_rate` decimal64(10, 3) NULL COMMENT \"分成比例\",\n" +
            "  `order_taking_service_income` decimal64(10, 3) NULL COMMENT \"接单服务费收入\",\n" +
            "  `reward_service_income` decimal64(10, 3) NULL COMMENT \"奖励服务费收入\",\n" +
            "  `fund_type` tinyint(4) NULL COMMENT \"资金流转类型.1:服务费收入订单;2:服务费支出订单\",\n" +
            "  `loan_base` decimal64(10, 3) NULL COMMENT \"贷款基数\",\n" +
            "  `product_name` varchar(512) NULL COMMENT \"商品名称\",\n" +
            "  `product_price` decimal64(10, 3) NULL COMMENT \"商品单价\",\n" +
            "  `send_status` tinyint(4) NULL COMMENT \"订单发货状态.0:未发货,1:已发货\",\n" +
            "  `sku_id` bigint(20) NULL COMMENT \"skuId\",\n" +
            "  `sec_tech_estimated_service_fee` decimal64(10, 3) NULL COMMENT \"预估技术服务费\",\n" +
            "  `sec_tech_service_fee` decimal64(10, 3) NULL COMMENT \"技术服务费\",\n" +
            "  `sec_tech_service_fee_rate` decimal64(10, 3) NULL COMMENT \"技术服务费率\",\n" +
            "  `sec_tech_settled_service_fee` decimal64(10, 3) NULL COMMENT \"结算技术服务费\",\n" +
            "  `talent_settled_commission` decimal64(10, 3) NULL COMMENT \"达人结算佣金收入\",\n" +
            "  `user_mchannel_dept_id` int(11) NULL COMMENT \"所属运营部门id\",\n" +
            "  `user_mchannel_dept_id_path` varchar(255) NULL COMMENT \"运营部门path\",\n" +
            "  `user_mchannel__dept_name_path` varchar(500) NULL COMMENT \"运营部门名称path\",\n" +
            "  `channel_name` varchar(256) NULL COMMENT \"渠道人员名字\",\n" +
            "  `partner_name` varchar(256) NULL COMMENT \"招商人员名字\",\n" +
            "  `user_carrier_name` varchar(256) NULL COMMENT \"运营人员名字\",\n" +
            "  `pid` varchar(128) NULL COMMENT \"pid\",\n" +
            "  `external_info` varchar(128) NULL COMMENT \"pid额外信息\",\n" +
            "  `xx_product_category_id` int(11) NULL COMMENT \"星选商品一级类目ID\",\n" +
            "  `matched_by` int(11) NULL COMMENT \"业务匹配方式\",\n" +
            "  `talent_tech_service_fee` decimal128(21, 2) NULL COMMENT \"抖客(达人)技术服务费\",\n" +
            "  `total_service_income` decimal64(10, 3) NULL COMMENT \"总服务费率\",\n" +
            "  `total_real_serviceIncome` decimal64(10, 3) NULL COMMENT \"总结算服务费\",\n" +
            "  `service_rate_ext` decimal64(10, 3) NULL COMMENT \"二级团长服务费率（订单一二级团长都是热度的场景用）\",\n" +
            "  `estimate_service_income_ext` decimal64(10, 3) NULL COMMENT \"二级团长预估服务费收入（订单一二级团长都是热度的场景用）\",\n" +
            "  `real_service_income_ext` decimal64(10, 3) NULL COMMENT \"二级团长实际服务费收入（订单一二级团长都是热度的场景用）\",\n" +
            "  `xx_order_type` int(11) NULL COMMENT \"星选订单类型.1:只领样,2:只使用添加橱窗功能,3:使用小程序领样并使用添加橱窗功能\",\n" +
            "  `pick_source_client_key` varchar(256) NULL COMMENT \"选品App client_key\",\n" +
            "  `is_reward_order` tinyint(4) NULL COMMENT \"是否奖励订单\",\n" +
            "  `reward_type` varchar(50) NULL COMMENT \"奖励类型\",\n" +
            "  `redu_douke_buyin_id` varchar(50) NULL COMMENT \"热度抖客百应id\",\n" +
            "  `redu_douke_buyin_name` varchar(64) NULL COMMENT \"独立抖客昵称\",\n" +
            "  `member_talent_id` int(11) NULL COMMENT \"小程序达人id\",\n" +
            "  `member_id` int(11) NULL COMMENT \"小程序用户id\",\n" +
            "  `dou_dian_author_openid` varchar(256) NULL COMMENT \"抖音抖店open_id\",\n" +
            "  `other_institution_id` bigint(20) NULL COMMENT \"其他机构id\",\n" +
            "  `other_institution_name` varchar(50) NULL COMMENT \"其他机构名称\",\n" +
            "  `other_colonel_buyin_id` varchar(64) NULL COMMENT \"其他团长百应ID\",\n" +
            "  `weixin_platform_shop_id` varchar(100) NULL COMMENT \"微信视频号店铺id\",\n" +
            "  `weixin_open_finderid` varchar(200) NULL COMMENT \"微信视频号达人id\",\n" +
            "  `mcn_buyin_id` varchar(50) NULL COMMENT \"MCN百应id\",\n" +
            "  `mcn_inst_id` varchar(50) NULL COMMENT \"MCN百应id\",\n" +
            "  `carrier_name` varchar(50) NULL COMMENT \"抖客运营人员名字\",\n" +
            "  `carrier_phone` varchar(32) NULL COMMENT \"抖客运营人员手机号\",\n" +
            "  `multiple_pay_time_flag` tinyint(4) NULL COMMENT \"订单付款时间是否有多个\",\n" +
            "  `xt_partner_id` int(11) NULL COMMENT \"星推招商id\",\n" +
            "  `xt_partner_name` varchar(256) NULL COMMENT \"星推招商名字\",\n" +
            "  `partner_final_service_rate` decimal64(10, 3) NULL COMMENT \"招商服务费率\",\n" +
            "  `partner_final_service_income` decimal64(10, 3) NULL COMMENT \"招商服务费\",\n" +
            "  `channel_final_service_rate` decimal64(10, 3) NULL COMMENT \"渠道服务费率\",\n" +
            "  `channel_final_service_income` decimal64(10, 3) NULL COMMENT \"渠道服务费\",\n" +
            "  `xt_uid` int(11) NULL COMMENT \"星推/星客所属会员id\",\n" +
            "  `distributor_right_type` varchar(255) NULL\n" +
            ")";

    public static void main(String[] args) throws JSQLParserException {
        CreateTable table = (CreateTable) CCJSqlParserUtil.parse(sql);
        for (ColumnDefinition columnDefinition : table.getColumnDefinitions()) {
            if (!"decimal".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())
                    && !"decimal128".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())
                    && !"decimal64".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())) {
                columnDefinition.getColDataType().setArgumentsStringList(null);
            }
            if ("decimal128".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())
                    || "decimal64".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())) {
                columnDefinition.getColDataType().setDataType("decimal");
            }
            columnDefinition.setColumnSpecs(null);
            if ("datetime".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())
                    || "varchar".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())) {
                columnDefinition.getColDataType().setDataType("string");
            }
        }
        table.setIndexes(null);
        System.out.println(table);
        generateStruct();
    }

    public static String parseSql(String sourceSql) throws JSQLParserException {
        CreateTable table = (CreateTable) CCJSqlParserUtil.parse(sourceSql);
        for (ColumnDefinition columnDefinition : table.getColumnDefinitions()) {
            if (!"decimal".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())
                    && !"decimal128".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())
                    && !"decimal64".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())) {
                columnDefinition.getColDataType().setArgumentsStringList(null);
            }
            if ("decimal128".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())
                    || "decimal64".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())) {
                columnDefinition.getColDataType().setDataType("decimal");
            }
            columnDefinition.setColumnSpecs(null);
            if ("varchar".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())
                    || "text".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())
                    || "json".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())) {
                columnDefinition.getColDataType().setDataType("string");
            }
            if ("datetime".equalsIgnoreCase(columnDefinition.getColDataType().getDataType())) {
                columnDefinition.getColDataType().setDataType("timestamp");
            }
        }
        table.setIndexes(null);
        return table.toString();
    }

    public static void generateStruct() {
        StringBuilder sb = new StringBuilder();
        Field[] fields = OrderExtEntity.class.getDeclaredFields();
        for (Field field : fields) {
            String type = "string";
            if (field.getType().equals(Long.class)) {
                type = "bigint";
            }
            if (field.getType().equals(BigDecimal.class)) {
                type = "decimal(9,2)";
            }
            sb.append(field.getName()).append(":").append(type).append(",");
        }
        System.out.println(sb);
    }

}
