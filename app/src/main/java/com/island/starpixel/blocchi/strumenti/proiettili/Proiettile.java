package com.island.starpixel.blocchi.strumenti.proiettili;
import com.island.starpixel.blocchi.solidi.*;
import com.island.starpixel.blocchi.strumenti.*;
public abstract class Proiettile extends Strumento
{
	public abstract double velocita();
	public abstract int potenza();
	public String descrizioneItaliano()
	{
		return"LO SPARI CONTRO I TUOI NEMICI CON LE ARMI";
	}
	public String descrizioneInglese()
	{
		return"YOU FIRE IT AGAINST YOUR ENEMIES WITH WEAPONS";
	}
}
