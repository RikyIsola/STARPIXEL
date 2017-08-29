package com.island.starpixel.blocchi.strumenti;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
public class Ferro extends Strumento
{
	public int immagine()
	{
		return R.drawable.ferro;
	}
	public String nomeItaliano()
	{
		return"FERRO";
	}
	public String nomeInglese()
	{
		return"IRON";
	}
	public String descrizioneItaliano()
	{
		return"MATERIALE MOLTO RESISTENTE";
	}
	public String descrizioneInglese()
	{
		return"VERY STRONG MATERIAL";
	}
	protected Blocco[]prodotti(Blocchi b)
	{
		return new Blocco[]{b.PortaFerro,b.MuroFerro,b.TerrenoFerro,b.ArcoFerro,b.AttrezziFerro};
	}
}
