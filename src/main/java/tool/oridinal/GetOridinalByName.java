package tool.oridinal;

/**
 * @author pengzhong
 * @since 2023/8/3
 */
public class GetOridinalByName {

    private static final String columns = "platform_order_id,platform_type,order_status,applet_peg,second_institution_peg,paid_time,settlement_time,refund_time,delivery_time,recv_time,platform_update_time,create_time,update_time,creator_id,modifier_id,is_delete,institution_id,second_institution_id,platform_product_id,buy_count,product_id,activity_id,second_activity_id,pay_amount,estimate_settlement_amount,settlement_amount,service_rate,estimate_service_income,real_service_income,second_real_commission,second_estimated_commission,second_commission_rate,tech_service_fee,tech_service_fee_rate,settled_tech_service_fee,system_multiple,weight_multiple,achievements_order_multiple,is_gen_order_multiple,shop_id,platform_shop_id,buyer_id,channel_talent_id,nickname,author_openid,shortid,partner_id,partner_dept_id,partner_dept_id_path,partner_dept_name_path,channel_id,channel_dept_id,channel_dept_id_path,channel_dept_name_path,user_carrier_id,final_service_rate,final_service_income,order_data_type,talent_estimated_commission,talent_real_commission,talent_commission_rate,uid,ext";

    public static void main(String[] args) {
        String target = "service_rate";
        String[] split = columns.split(",");
        int i = 0;
        for (String s : split) {
            if (s.equals(target)) {
                System.out.println(i);
            }
            i++;
        }
    }

}
