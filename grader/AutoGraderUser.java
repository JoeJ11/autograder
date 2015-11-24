import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class AutoGraderUser
{
	public static final int total = 50;
	public static final String[] text = {"淘宝名品名店", "喷嚏网铂程", "董坚", "昆山市公安局新镇所",
											"史丹利澳門", "昆山公安周市派出所", "陈宝存", "昆山市公安局淀山湖所", "天翼宝网",
											"科技墨舞", "蓝旗主的微博版史记", "兜率仔仔", "真爱网", "嘉人网编辑Faith_Song", "虎扑足球",
											"金承龙微博", "江苏昆山公安石浦所", "新浪体育视频滚动播报", "昆山公安蓬朗派出所", "新浪体育",
											"闫珉川", "汇通网_FX678", "微天下", "通信信息报", "辽视第一时间", "头条新闻", "数字尾巴",
											"新浪娱乐视频滚动播报", "巴菲特SMS", "周京平", "O_______________O", "昆山市公安局千灯所",
											"怡居环保设备有限公司", "昆山公安陆家派出所", "青岛交通广播FM897", "范梅强", "淘宝客联盟官方微博",
											"海德福科技有限公司", "万达影城销售哥懒汉", "劳毅波广州大波波", "昆山公安局青阳派出所", "侯宁",
											"昆山公安平安阳澄湖", "中国新闻抢先报", "老辣陈香", "昆山市公安局同心所", "河北解放",
											"思饭辙", "王小亚", "蔡瀾", "昆山市公安局兵希所"};
	public static final int[] number = {3397, 2550, 2208, 2159, 2146, 1963, 1872, 1835, 1801, 1770, 1749, 1624, 1603, 1594, 1567, 1505, 1498,
											1452, 1444, 1411, 1361, 1359, 1357, 1347, 1324, 1305, 1302, 1293, 1289, 1283, 1282, 1275, 1268,
											1264, 1260, 1247, 1228, 1226, 1210, 1210, 1209, 1202, 1202, 1194, 1190, 1183, 1170, 1168, 1166, 1163, 1163};

	public static void main(String[] args) throws IOException
	{
		String dic = args[0];
		File i = new File(dic + "/answer");
		File o = new File(dic + "/response");
		InputStreamReader in = new InputStreamReader(new FileInputStream(i),"UTF-8");
		BufferedReader input = new BufferedReader(in);
		OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(o), "UTF-8");
		String t;
		boolean[] exist = new boolean[51];
		for(int j = 0; j < 51; j++)
			exist[j] = false;
		int num = 0;
		double point = 0;
		while((t= input.readLine()) !=null)
		{
			num++;
			// System.out.println(t);
			String[] pair = t.split("\\s+");
			if (pair.length == 1 ) { 
				pair = t.split("\t");
			}
			// for (int j = 0; j != pair.length; j++) {
			// 	 System.out.println(pair[j]);
			// }
			if(pair.length != 2){
				continue;
			}
			if(!isInteger(pair[0]))
				continue;
			int n = Integer.parseInt(pair[0]);
			for(int j = 0; j < text.length; j++)
			{
				if(text[j].equals(pair[1]) && (!exist[j]))
				{
					point += 2 - 2 * Math.min(Math.abs(n - number[j] + 0.0)/number[j], 1);
					exist[j] = true;
					if(j == 50)
						exist[49] = true;
					if(j == 49)
						exist[50] = true;
					break;
				}
			}
			if(num == total)
				break;
		}
		input.close();
		output.write(Integer.toString((int)point));
		output.write("\n");
		output.write("100\n");
		output.write("<p>No comment provided.</p>");
		output.close();
	}

	public static boolean isInteger(String value) {
		  try {
			     Integer.parseInt(value);
				    return true;
					  } catch (NumberFormatException e) {
						     return false;
							   }
		   }
}
