package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;

public class MuroFerro extends Solido
{
	public int vita()
	{
		return 150;
	}
	public int immagine()
	{
		return R.drawable.muroferro;
	}
	public String nomeItaliano()
	{
		return"MURO FERRO";
	}
	public String nomeInglese()
	{
		return"IRON WALL";
	}
	public String descrizioneItaliano()
	{
		return"MURO MOLTO RESISTENTE USATO PER COSTRUIRE NAVI";
	}
	public String descrizioneInglese()
	{
		return"VERY STRONG WALL USED TO BUILD SHIPS";
	}
}
