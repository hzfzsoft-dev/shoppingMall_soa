package org.fzsoft.shoppingmall.utils.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Boyce 2016年6月28日 下午4:31:43 进行double类型的数据四则运算。 dot表示小数点后保留几位，默认为6.
 */
public class Arith {

	/**
	 * 加法
	 *
	 * @param dot
	 *            保留小数点
	 * @param numbers
	 *            数字类型
	 * @return
	 */
	public static double add(int dot, Number... numbers) {
		return getAirth(dot, AirthType.ADD.getValue(), trimToZero(numbers));

	}

	/**
	 * 减法
	 *
	 * @param dot
	 *            保留小数点
	 * @param numbers
	 *            第一个是被减数
	 * @return
	 */
	public static double subtract(int dot, Number... numbers) {

		return getAirth(dot, AirthType.SUBTRACT.getValue(), trimToZero(numbers));

	}

	/**
	 * 乘法
	 *
	 * @param dot
	 *            保留小数点
	 * @param numbers
	 *            乘法的因子
	 * @return
	 */
	public static double multiplys(int dot, Number... numbers) {

		return getAirth(dot, AirthType.MULTIPLY.getValue(), numbers);

	}

	/**
	 * 除法
	 *
	 * @param dot
	 *            保留小数点
	 * @param numbers
	 *            除数，第一个是除数
	 * @return
	 */
	public static double divides(int dot, Number... numbers) {

		return getAirth(dot, AirthType.DIVIDE.getValue(), numbers);

	}

	/**
	 * 设置小数点保留几位
	 *
	 * @auth yehx
	 * @date 2016年7月9日
	 */
	public static double setScale(Double x1, int scale) {
		return BigDecimal.valueOf(x1).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * 保留几位小数，不四舍五入
	 *
	 * @auth yehx
	 * @date 2016年7月9日
	 */
	public static double setScaleDown(Double x1, int scale) {
		return BigDecimal.valueOf(x1).setScale(scale, RoundingMode.DOWN).doubleValue();
	}

	/**
	 * 综合的运算
	 *
	 * @param dot
	 *            保留精度
	 * @param airthType
	 *            运算的类型
	 * @param numbers
	 *            需要运算的数字
	 * @return
	 */
	private static double getAirth(int dot, int airthType, Number... numbers) {

		if (numbers == null || numbers.length == 0) {
			return 0.0;
		}
		BigDecimal bigDecimal = new BigDecimal(String.valueOf(numbers[0]));

		for (int i = 1; i < numbers.length; i++) {

			switch (airthType) {

			case 0:
				bigDecimal = bigDecimal.add(new BigDecimal(String.valueOf(numbers[i])));

				break;
			case 1:

				bigDecimal = bigDecimal.divide(new BigDecimal(String.valueOf(numbers[i])), dot, RoundingMode.HALF_UP);
				break;
			case 2:
				bigDecimal = bigDecimal.subtract(new BigDecimal(String.valueOf(numbers[i])));
				break;
			case 3:
				bigDecimal = bigDecimal.multiply(new BigDecimal(String.valueOf(numbers[i])));
				break;

			}

		}

		return bigDecimal.setScale(dot, RoundingMode.HALF_UP).doubleValue();

	}

	/**
	 * 运算的类型
	 */
	private enum AirthType {

		ADD("add", 0), DIVIDE("divide", 1), SUBTRACT("subtract", 2), MULTIPLY("multiply", 3);
		private String key;
		private int value;

		AirthType(String key, int value) {
			this.key = key;
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}


	private static Number[] trimToZero(Number... d) {

		if (d == null) {
			return new Number[0];
		}

		Number[] result = new Number[d.length];
		for (int i = 0; i < d.length; i++) {
			result[i] = (d[i] == null ? new Integer(0) : d[i]);
		}

		return result;
	}
}
