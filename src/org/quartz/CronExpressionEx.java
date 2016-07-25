package org.quartz;

import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * 
 * @author Administrator
 */
public class CronExpressionEx {
	public static final Integer ALL_SPEC = new Integer(CronExpression.ALL_SPEC_INT);
	public static final int NO_SPEC_INT = 98; // '?'

	private String secondsExp;
	private String minutesExp;
	private String hoursExp;
	private String daysOfMonthExp;
	private String monthsExp;
	private String daysOfWeekExp;
	private String yearExp;
	private CronExpression cronExpression;

	public CronExpressionEx(String cronExpression) throws ParseException {
		this.cronExpression = new CronExpression(cronExpression);
		StringTokenizer exprsTok = new StringTokenizer(cronExpression, " ", false);
		secondsExp = exprsTok.nextToken().trim();
		minutesExp = exprsTok.nextToken().trim();
		hoursExp = exprsTok.nextToken().trim();
		daysOfMonthExp = exprsTok.nextToken().trim();
		monthsExp = exprsTok.nextToken().trim();
		if (exprsTok.hasMoreTokens()) {
			daysOfWeekExp = exprsTok.nextToken().trim();
		}
		if (exprsTok.hasMoreTokens()) {
			yearExp = exprsTok.nextToken().trim();
		}
	}

	public TreeSet<Integer> getSecondsSet() {
		return cronExpression.getSet(CronExpression.SECOND);
	}

	public String getSecondsField() {
		return getNotEmptyString(secondsExp, getExpressionSetSummary(getSecondsSet()));
	}

	private String getExpressionSetSummary(TreeSet<Integer> set) {
		return cronExpression.getExpressionSetSummary(set);
	}

	public TreeSet<Integer> getMinutesSet() {
		return cronExpression.getSet(CronExpression.MINUTE);
	}

	public String getMinutesField() {
		return getNotEmptyString(minutesExp, getExpressionSetSummary(getMinutesSet()));
	}

	public TreeSet<Integer> getHoursSet() {
		return cronExpression.getSet(CronExpression.HOUR);
	}

	public String getHoursField() {
		return getNotEmptyString(hoursExp, getExpressionSetSummary(getHoursSet()));
	}

	public TreeSet<Integer> getDaysOfMonthSet() {
		return cronExpression.getSet(CronExpression.DAY_OF_MONTH);
	}

	public String getDaysOfMonthField() {
		return getNotEmptyString(daysOfMonthExp, getExpressionSetSummary(getDaysOfMonthSet()));
	}

	public TreeSet<Integer> getMonthsSet() {
		return cronExpression.getSet(CronExpression.MONTH);
	}

	public String getMonthsField() {
		return getNotEmptyString(monthsExp, getExpressionSetSummary(getMonthsSet()));
	}

	public TreeSet<Integer> getDaysOfWeekSet() {
		return cronExpression.getSet(CronExpression.DAY_OF_WEEK);
	}

	public String getDaysOfWeekField() {
		return getNotEmptyString(daysOfWeekExp, getExpressionSetSummary(getDaysOfWeekSet()));
	}

	public TreeSet<Integer> getYear() {
		return cronExpression.getSet(CronExpression.YEAR);
	}

	public String getYearField() {
		return getNotEmptyString(yearExp, getExpressionSetSummary(getYear()));
	}

	private String getNotEmptyString(String primary, String second) {
		if (primary != null && primary.trim().length() > 0) {
			return primary;
		} else if (second != null && second.trim().length() > 0) {
			return second;
		} else {
			return "";
		}
	}

	public String getCronExpression() {
		return cronExpression.getCronExpression();
	}

	public CronExpression getCronExpressionObj() {
		return cronExpression;
	}

	public static boolean isValidExpression(String cronExpression) {
		return CronExpression.isValidExpression(cronExpression);
	}

	public Date getNextValidTimeAfter(Date dd) {
		return cronExpression.getNextValidTimeAfter(dd);
	}

	public Date getNextInvalidTimeAfter(Date dd) {
		return cronExpression.getNextInvalidTimeAfter(dd);
	}

	@Override
	public String toString() {
		return "CronExpressionEx [secondsExp=" + secondsExp + ", minutesExp=" + minutesExp + ", hoursExp=" + hoursExp
				+ ", daysOfMonthExp=" + daysOfMonthExp + ", monthsExp=" + monthsExp + ", daysOfWeekExp=" + daysOfWeekExp
				+ ", yearExp=" + yearExp + "]";
	}

	public String buildNatureLanguage() {
		StringBuilder sb = new StringBuilder();
		if (yearExp != null && yearExp.length() > 0 && !"*".equals(yearExp)) {
			if (yearExp.contains("/")) {
				String[] split = yearExp.split("/");
				String from = split[0];
				String pre = split[1];
				if ("*".equals(from)) {
					sb.append("从当年");
				} else {
					sb.append("从").append(from).append("年");
				}
				sb.append("开始,间隔").append(pre).append("年，");
			} else if (yearExp.contains("-")) {
				String[] split = yearExp.split("-");
				String from = split[0];
				String to = split[1];
				if ("*".equals(from)) {
					sb.append("从当年");
				} else {
					sb.append("从").append(from).append("年");
				}
				sb.append("开始,到").append(to).append("年，");
			} else {
				sb.append("在").append(yearExp).append("年，");
			}
		}
		if (!"?".equals(daysOfWeekExp)) {

		}
		if (!"*".equals(monthsExp)) {

		}
		if (!"*".equals(daysOfMonthExp)) {

		}
		if (!"*".equals(hoursExp)) {

		}
		if (!"*".equals(minutesExp)) {

		}
		// if (!"*".equals(secondsExp)) {
		// if (secondsExp.contains("/")) {
		// sb.append("每分钟内");
		// String[] split = secondsExp.split("/");
		// String from = split[0];
		// String pre = split[1];
		// if (!"*".equals(from)) {
		// sb.append("从第").append(from).append("秒开始,");
		// }else {
		// sb.append("从第0秒开始");
		// }
		// sb.append("每隔").append(pre).append("秒");
		// } else if (secondsExp.contains("-")) {
		// if (secondsExp.contains("*")) {
		// sb.append("每1秒");
		// } else {
		// String[] split = secondsExp.split("-");
		// int from = Integer.parseInt(split[0]);
		// int to = Integer.parseInt(split[1]);
		// if(from>to){
		// sb.append("每1秒");
		// }else if (from == to) {
		// sb.append("每分钟内第").append(from).append("秒");
		// }else{
		// sb.append("每分钟内从第").append(from).append("秒开始");
		// sb.append("到第").append(to).append("秒之间每秒");
		// }
		// }
		// } else {
		// sb.append(secondsExp).append("秒");
		// }
		// } else {
		// sb.append("每1秒");
		// }
		BuildStep(sb, monthsExp, "月", "年", false);
		BuildStep(sb, hoursExp, "点", "天", false);
		BuildStep(sb, minutesExp, "分", "小时", false);
		BuildStep(sb, secondsExp, "秒", "分钟", true);
		sb.append("执行一次");
		return sb.toString();
	}

	public StringBuilder BuildStep(StringBuilder sb, String exp, String unit, String parentUnit, boolean append) {
		if (!"*".equals(exp)) {
			if (exp.contains("/")) {
				sb.append("每").append(parentUnit).append("内");
				String[] split = exp.split("/");
				String from = split[0];
				String pre = split[1];
				if (!"*".equals(from)) {
					sb.append("从第").append(from).append(unit).append("开始，");
				} else {
					sb.append("从第0").append(unit).append("开始");
				}
				sb.append("间隔").append(pre).append(unit);
			} else if (exp.contains("-")) {
				if (exp.contains("*")) {
					sb.append("每1").append(unit);
				} else {
					String[] split = exp.split("-");
					int from = Integer.parseInt(split[0]);
					int to = Integer.parseInt(split[1]);
					if (from > to) {
						sb.append("每").append(unit);
					} else if (from == to) {
						sb.append("每").append(parentUnit).append("第").append(from).append(unit);
					} else {
						sb.append("每").append(parentUnit).append("从第").append(from).append(unit).append("开始");
						sb.append("到第").append(to).append(unit).append("之间每").append(unit);
					}
				}
			} else {
				sb.append("每").append(parentUnit).append("第").append(exp).append(unit);
			}
			sb.append("，");
		} else {
			if (append) {
				sb.append("每").append(unit);
				sb.append("，");
			}
		}
		return sb;
	};
}
