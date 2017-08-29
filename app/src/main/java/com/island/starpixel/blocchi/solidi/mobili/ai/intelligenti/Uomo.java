package com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.strumenti.lanciati.*;
public class Uomo extends Intelligente
{
	public Lanciato arma(Blocchi b)
	{
		return b.Laser1;
	}
	public int immagine()
	{
		return R.drawable.uomo;
	}
	public String nomeItaliano()
	{
		return"UOMO";
	}
	public String nomeInglese()
	{
		return"MAN";
	}
	public String descrizioneItaliano()
	{
		return"É UN TUO ALLEATO";
	}
	public String descrizioneInglese()
	{
		return"HE IS ONE OF YOUR ALLIES";
	}
	public int vita()
	{
		return 150;
	}
	public int team(Blocchi b)
	{
		return b.uomini;
	}
	public int suono()
	{
		return R.raw.evil_male_death;
	}
}
