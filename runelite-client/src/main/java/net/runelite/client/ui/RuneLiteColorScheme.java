package net.runelite.client.ui;

import java.awt.Color;

public class RuneLiteColorScheme
{
	public static String primaryColor = MaterialColor.BLUE_GRAY_900.toHex();
	public static String primaryColorLight = MaterialColor.BLUE_GRAY_800.toHex();
	public static String primaryColorDark = new MaterialColor(MaterialColor.BLUE_GRAY_900.darker()).toHex();

	public static String secondaryColor = MaterialColor.GREEN_A200.toHex();
	public static String secondaryColorLight = new MaterialColor(MaterialColor.GREEN_A200.brighter()).toHex();
	public static String secondaryColorDark = new MaterialColor(MaterialColor.GREEN_A200.darker()).toHex();
}
