package tool;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author pengzhong
 * @since 2023/5/22
 */
public class SplitTest {

    public static void main(String[] args) {
        String[] xing = getTables().split("\n");
        List<String> xingxuanTables = Lists.newArrayList();

        for (String s : xing) {
            xingxuanTables.add(s);
        }
        //System.out.println();
        String[] split = getUsedTables().split(",             ");
        List<String> usedTables = Lists.newArrayList();
        for (String s : split) {
            usedTables.add(s);
        }
        List<String> needUseTables = Lists.newArrayList();
        List<String> notUseTables = Lists.newArrayList();
        for (String xingxuanTable : xingxuanTables) {
            if (usedTables.contains(xingxuanTable)){
                needUseTables.add(xingxuanTable);
            } else {
                notUseTables.add(xingxuanTable);
            }
        }
        System.out.println(needUseTables);
        System.out.println(notUseTables);
    }


    public static String getUsedTables() {
        return "shop_estimate_service_income_offline,             jing_pin_dao_chu_lai202,             fan_xing_m_c_n470,             yuan_shi_tuan_zhang_yue422,             weixin_order_offline,             xing_ke_ding_dan_suo027,             ji_gou_yong_jin_yue637,             yuan_shi_tuan_zhang_yue873,             xing_tui_m_c_n125,             fan_xing_ji_gou_hai962,             yue_yue_zhang_dan_shang390,             shang_pin_i_d_x656,             m_c_n887,             other_product,             xing_tui_er_yue178,             douyou_hotsell_sea_offline,             dou_yin_shang_pin_i808,             team_talent_offline,             manager_team_offline,             team_order_gmv_offline_v2,             douyin_interval_data_offine_v2,             fan_xing_tuan_zhang_yue575,             top50_talent_offline_v2,             top50_shop_offline_v2,             tuan_zhang_kai_bo_zheng125,             hua_ming_ce_yue936,             sheng_xian960,             qian_yue_da_ren_ming_dan075,             ji_rou_shan_shi_chang699,             re_du_shuang564,             user_retained_all,             user_retained_add,             shop_retained_all,             re_du_xing_xuan_ji605,             zhu_tui_pin_lie_biao_x085,             tuan_zhang_fu_wu_fei897,             yong_hu_yun_ying_fei230,             xingxuan_category_relation,             shuang_huo_pan253,             douyin_shop_data_offline,             manage_talent_daily_offline,             manage_daily_offline,             ksyeji_partner_offline,             ksyeji_channel_offline,             dyyeji_partner_offline,             douyin_product_gmv_offine,             kuaishou_partner_offline,             kuaishou_channel_offline,             institution_daily_offline,             douyin_prodcut_data_offline,             douyin_partner_offline,             douyin_channel_offline,             douyin_activity_data_offline,             redu_product_activity,             redu_product,             redu_activity,             san_bao_shang_pin_ming_dan883,             shop_retained,             user_retained,             fanxing_talnet_offline,             er_ji_tuan_zhang_yue100,             product_statistics_offline,             douyin_manage_data_offine,             channel_business_offline,             fan_xing_2_0_2761,             dou_yin_yue_zhang_dan235,             product_category_statistics_offline,             partner_achievement_offline,             douyin_talent_data_offine,             douyin_summary_data_offine,             douyin_settle_data_offine,             douyin_moving_sell_data_offine,             douyin_business_offline,             business_daily_report_offline,             member_auth_offline,             applet_talent_data_details_offline,             applet_product_date_offine,             shop_share,             kuai_shou_talnet_section_offline,             kuai_shou_offline,             dou_yin_7_yue_3866,             redu_order,             xing_tui_7_yue_ding_dan726,             xin_yu_yi_ti_bao872,             fan_xing_shu_ju_2810,             hai_tao_fu_wu_fei502,             tuan_zhang_4_yue_fu413,             p_i_x459,             order_center,             ckuaishou_order,             team,             shop,             product_spec,             product_category,             product,             partner,             order_talent,             member_talent_douyin_order,             member_talent,             member_mission,             member_add_store,             member,             mdouyin_order,             group,             channel_talent,             channel_order_talent,             channel,             cdouyin_order_settle,             cdouyin_order_refund,             cdouyin_order_pay,             cdouyin_order_confirm,             cdouyin_order2022,             cdouyin_order2021,             branch,             admin_auth_item_child,             admin_auth_item,             admin_auth_assignment,             admin";
    }
    
    public static String getTables() {
        return "activities\n" +
                "activity_sign_up\n" +
                "admin\n" +
                "admin_auth_assignment\n" +
                "admin_auth_item\n" +
                "admin_auth_item_child\n" +
                "admin_auth_rule\n" +
                "admin_log\n" +
                "advance_payment_order\n" +
                "anonymous_feedback\n" +
                "area\n" +
                "audit_talent_business\n" +
                "auto_reply\n" +
                "background\n" +
                "banner\n" +
                "bidding\n" +
                "blacklist\n" +
                "branch\n" +
                "brand\n" +
                "buy_sample_record\n" +
                "buyin_csv_file\n" +
                "cdouyin_order2021\n" +
                "cdouyin_order2022\n" +
                "cdouyin_order2023\n" +
                "cdouyin_order2024\n" +
                "cdouyin_order2025\n" +
                "channel\n" +
                "channel_access_token\n" +
                "channel_album\n" +
                "channel_album_product\n" +
                "channel_album_share\n" +
                "channel_browsing\n" +
                "channel_duty_talent_permeability\n" +
                "channel_duty_talent_relation\n" +
                "channel_favorite\n" +
                "channel_intention_excel\n" +
                "channel_intention_talent\n" +
                "channel_message\n" +
                "channel_order\n" +
                "channel_order_express\n" +
                "channel_order_talent\n" +
                "channel_order_talent_pay\n" +
                "channel_support\n" +
                "channel_talent\n" +
                "channel_talent_ext\n" +
                "channel_talent_tag\n" +
                "channel_talent_tag_relation\n" +
                "city\n" +
                "code\n" +
                "convert_video\n" +
                "cooperation\n" +
                "cooperation_ip\n" +
                "cooperation_product\n" +
                "course\n" +
                "course_attend\n" +
                "course_chapter\n" +
                "course_tag\n" +
                "custom_copy\n" +
                "directional_relation\n" +
                "district\n" +
                "douyin_product_category\n" +
                "duty_plan\n" +
                "duty_talent_record\n" +
                "employee_performance\n" +
                "endorsement\n" +
                "excel_custom\n" +
                "exclusive_product\n" +
                "export_record\n" +
                "express\n" +
                "favorite\n" +
                "feedback\n" +
                "free_talent_examine\n" +
                "free_talent_examine_record\n" +
                "group\n" +
                "hot_single_product\n" +
                "hot_single_product_ext\n" +
                "income_record_bak50071\n" +
                "income_record2021\n" +
                "income_record2022\n" +
                "income_record2023\n" +
                "income_record2024\n" +
                "income_record2025\n" +
                "inside_feedback\n" +
                "jieba_word\n" +
                "jobs\n" +
                "kuaishou_authority\n" +
                "kwaixiaodian_csv_file\n" +
                "login_code\n" +
                "login_log\n" +
                "main_product_talent\n" +
                "main_push_tag\n" +
                "manage_access_token\n" +
                "manage_power\n" +
                "material\n" +
                "material_member\n" +
                "material_script\n" +
                "material_video\n" +
                "material_video_download\n" +
                "matrix_talent\n" +
                "matrix_talent_detail\n" +
                "mdouyin_order2021\n" +
                "mdouyin_order2022\n" +
                "mdouyin_order2023\n" +
                "mdouyin_order2024\n" +
                "mdouyin_order2025\n" +
                "member\n" +
                "member_access_token\n" +
                "member_access_token_offical\n" +
                "member_activist_record\n" +
                "member_add_store\n" +
                "member_collection_account\n" +
                "member_cooperation\n" +
                "member_ext\n" +
                "member_invitation\n" +
                "member_invitation_rebate\n" +
                "member_like_cate\n" +
                "member_message\n" +
                "member_mission\n" +
                "member_post\n" +
                "member_selection_cart\n" +
                "member_talent\n" +
                "member_talent_contract\n" +
                "member_talent_delete\n" +
                "member_talent_ext\n" +
                "member_talent_invitation\n" +
                "menu\n" +
                "merchant\n" +
                "merchant_access_token\n" +
                "migration\n" +
                "mini_program_publish\n" +
                "mission\n" +
                "mission_cooperate\n" +
                "mission_direct_talent\n" +
                "mission_ext\n" +
                "mission_feedback_answer\n" +
                "mission_feedback_question\n" +
                "mission_guide\n" +
                "mission_investment\n" +
                "mission_invitation\n" +
                "mission_topic\n" +
                "mission_topic_relation\n" +
                "mission_video\n" +
                "mission_video_download\n" +
                "mission_video_history\n" +
                "operate_special_area\n" +
                "operate_special_area_relation\n" +
                "order\n" +
                "order_express\n" +
                "order_pay\n" +
                "order_talent\n" +
                "order_talent_evaluate\n" +
                "order_talent_repeat\n" +
                "outer_talent\n" +
                "outer_talent_ext\n" +
                "partner\n" +
                "partner_message\n" +
                "pay_transaction_record\n" +
                "performance\n" +
                "poster\n" +
                "product\n" +
                "product_blind_send\n" +
                "product_category\n" +
                "product_cooperate\n" +
                "product_custom\n" +
                "product_data_market_cache\n" +
                "product_express_relation\n" +
                "product_ext\n" +
                "product_ext_field\n" +
                "product_follow_up_record\n" +
                "product_fx\n" +
                "product_fx_fail\n" +
                "product_image\n" +
                "product_keyword\n" +
                "product_label\n" +
                "product_label_category_relation\n" +
                "product_label_relation\n" +
                "product_main_relation\n" +
                "product_operation_record\n" +
                "product_pool\n" +
                "product_rank\n" +
                "product_recommend\n" +
                "product_sale\n" +
                "product_seckill\n" +
                "product_seconds_kill\n" +
                "product_sellway\n" +
                "product_sellway_relation\n" +
                "product_spec\n" +
                "product_special\n" +
                "product_tag\n" +
                "product_tag_hot\n" +
                "product_tag_relation\n" +
                "product_update_record\n" +
                "project\n" +
                "province\n" +
                "search_terms\n" +
                "second_partner_apply\n" +
                "setting\n" +
                "shake_shop_authority\n" +
                "shop\n" +
                "shop_deposit_record\n" +
                "shop_insured\n" +
                "shop_intention\n" +
                "shop_share\n" +
                "show_direct_member\n" +
                "special\n" +
                "substitute_talent_order\n" +
                "support\n" +
                "support_class\n" +
                "support_read_record\n" +
                "talent_attribute_data\n" +
                "talent_data_market_cache\n" +
                "talent_tag\n" +
                "talent_tag_relation\n" +
                "task\n" +
                "task_attend\n" +
                "task_product\n" +
                "team\n" +
                "template_message_config\n" +
                "template_send_log\n" +
                "test_gaolei\n" +
                "treaty\n" +
                "video_mps\n" +
                "wallet\n" +
                "website_application\n" +
                "website_application_education\n" +
                "website_application_work\n" +
                "website_business\n" +
                "website_business_introduce\n" +
                "website_case\n" +
                "website_category\n" +
                "website_contact_us\n" +
                "website_cooperation\n" +
                "website_cooperation_type\n" +
                "website_ext_field\n" +
                "website_info_list\n" +
                "website_information\n" +
                "website_recruit_job\n" +
                "website_recruit_post\n" +
                "website_recruit_type\n" +
                "wechat_menu\n" +
                "withdrawal_record2021\n" +
                "withdrawal_record2022\n" +
                "withdrawal_record2023\n" +
                "withdrawal_record2024\n" +
                "withdrawal_record2025\n" +
                "xingxuan_category_relation";
    }

}
