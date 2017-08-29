package com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti.combattivi;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
public class Zombie extends Combattivo
{
	public int immagine()
	{
		return R.drawable.zombie;
	}
	public String nomeItaliano()
	{
		return"ZOMBIE";
	}
	public String nomeInglese()
	{
		return"ZOMBIE";
	}
	public int vita()
	{
		return 150;
	}
	public int team(Blocchi b)
	{
		return b.zombie;
	}
	public int suono()
	{
		return R.raw.zombie_death;
	}
}
