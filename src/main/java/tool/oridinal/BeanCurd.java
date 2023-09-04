package tool.oridinal;

import tool.ActivityTest;

import java.lang.reflect.Field;

/**
 * @author pengzhong
 * @since 2023/8/3
 */
public class BeanCurd {

    public static void main(String[] args) {
        Class aClass = ActivityTest.class;
        StringBuilder outPut = new StringBuilder();
        StringBuilder intPut = new StringBuilder();
        for (Field declaredField : aClass.getDeclaredFields()) {
            //System.out.println(declaredField);

            if (declaredField.getType().equals(String.class)) {
                outPut.append("if (StrUtil.isNotEmpty(teamName)) {\n");
                outPut.append("dataOutput.writeBytes(").append(declaredField.getName()).append(");\n");
                outPut.append("}");
                intPut.append("this.").append(declaredField.getName()).append(" = ").append("dataInput.readLine();\n");
            }
            if (declaredField.getType().equals(int.class)) {
                outPut.append("dataOutput.writeInt(").append(declaredField.getName()).append(");\n");
                intPut.append("this.").append(declaredField.getName()).append(" = ").append("dataInput.readInt();\n");
            }
            if (declaredField.getType().equals(long.class)) {
                outPut.append("dataOutput.writeLong(").append(declaredField.getName()).append(");\n");
                intPut.append("this.").append(declaredField.getName()).append(" = ").append("dataInput.readLong();\n");
            }
        }
        System.out.println(outPut);
        System.out.println(intPut);
    }

}
