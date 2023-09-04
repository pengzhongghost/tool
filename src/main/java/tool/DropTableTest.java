package tool;

/**
 * @author pengzhong
 * @since 2023/5/18
 */
public class DropTableTest {

    public static void main(String[] args) {
        String[] split = getDropTableSql().split(",");
        for (String s : split) {
            System.out.println("drop table " + s + ";");
        }

    }




    public static String getDropTableSql(){
        return "activities, activity_sign_up, admin_auth_rule, admin_log, advance_payment_order, anonymous_feedback, area, audit_talent_business, auto_reply, background, banner, bidding, blacklist, brand, buy_sample_record, buyin_csv_file, cdouyin_order2023, cdouyin_order2024, cdouyin_order2025, channel_access_token, channel_album, channel_album_product, channel_album_share, channel_browsing, channel_duty_talent_permeability, channel_duty_talent_relation, channel_favorite, channel_intention_excel, channel_intention_talent, channel_message, channel_order, channel_order_express, channel_order_talent_pay, channel_support, channel_talent_ext, channel_talent_tag, channel_talent_tag_relation, city, code, convert_video, cooperation, cooperation_ip, cooperation_product, course, course_attend, course_chapter, course_tag, custom_copy, directional_relation, district, douyin_product_category, duty_plan, duty_talent_record, employee_performance, endorsement, excel_custom, exclusive_product, export_record, express, favorite, feedback, free_talent_examine, free_talent_examine_record, hot_single_product, hot_single_product_ext, income_record_bak50071, income_record2021, income_record2022, income_record2023, income_record2024, income_record2025, inside_feedback, jieba_word, jobs, kuaishou_authority, kwaixiaodian_csv_file, login_code, login_log, main_product_talent, main_push_tag, manage_access_token, manage_power, material, material_member, material_script, material_video, material_video_download, matrix_talent, matrix_talent_detail, mdouyin_order2021, mdouyin_order2022, mdouyin_order2023, mdouyin_order2024, mdouyin_order2025, member_access_token, member_access_token_offical, member_activist_record, member_collection_account, member_cooperation, member_ext, member_invitation, member_invitation_rebate, member_like_cate, member_message, member_post, member_selection_cart, member_talent_contract, member_talent_delete, member_talent_ext, member_talent_invitation, menu, merchant, merchant_access_token, migration, mini_program_publish, mission, mission_cooperate, mission_direct_talent, mission_ext, mission_feedback_answer, mission_feedback_question, mission_guide, mission_investment, mission_invitation, mission_topic, mission_topic_relation, mission_video, mission_video_download, mission_video_history, operate_special_area, operate_special_area_relation, order, order_express, order_pay, order_talent_evaluate, order_talent_repeat, outer_talent, outer_talent_ext, partner_message, pay_transaction_record, performance, poster, product_blind_send, product_cooperate, product_custom, product_data_market_cache, product_express_relation, product_ext, product_ext_field, product_follow_up_record, product_fx, product_fx_fail, product_image, product_keyword, product_label, product_label_category_relation, product_label_relation, product_main_relation, product_operation_record, product_pool, product_rank, product_recommend, product_sale, product_seckill, product_seconds_kill, product_sellway, product_sellway_relation, product_special, product_tag, product_tag_hot, product_tag_relation, product_update_record, project, province, search_terms, second_partner_apply, setting, shake_shop_authority, shop_deposit_record, shop_insured, shop_intention, show_direct_member, special, substitute_talent_order, support, support_class, support_read_record, talent_attribute_data, talent_data_market_cache, talent_tag, talent_tag_relation, task, task_attend, task_product, template_message_config, template_send_log, test_gaolei, treaty, video_mps, wallet, website_application, website_application_education, website_application_work, website_business, website_business_introduce, website_case, website_category, website_contact_us, website_cooperation, website_cooperation_type, website_ext_field, website_info_list, website_information, website_recruit_job, website_recruit_post, website_recruit_type, wechat_menu, withdrawal_record2021, withdrawal_record2022, withdrawal_record2023, withdrawal_record2024, withdrawal_record2025";
    }

}
