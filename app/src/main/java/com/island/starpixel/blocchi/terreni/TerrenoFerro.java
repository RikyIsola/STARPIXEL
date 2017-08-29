package com.island.starpixel.blocchi.terreni;
import com.island.starpixel.*;
public class TerrenoFerro extends Terreno
{
	public int vita()
	{
		return 150;
	}
	public String descrizioneInglese()
	{
		return"VERY STRONG TERRAIN USED TO BUILD SHIPS";
	}
	public String descrizioneItaliano()
	{
		return"TERRENO MOLTO RESISTENTE USATO PER COSTRUIRE NAVI";
	}
	public String nomeItaliano()
	{
		return"TERRENO FERRO";
	}
	public String nomeInglese()
	{
		return"IRON FLOR";
	}
	public int immagine()
	{
		return R.drawable.terrenoferro;
	}
}
