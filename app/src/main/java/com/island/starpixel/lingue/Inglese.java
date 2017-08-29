package com.island.starpixel.lingue;
import com.island.starpixel.blocchi.*;
public class Inglese extends Lingua
{
	public String scudi()
	{
		return"SHIELDS";
	}
	public String toccaALungo()
	{
		return"LONG PRESS AN OBJECT FOR DESCRIPTION";
	}
	public String inventario()
	{
		return"INVENTORY";
	}
	public String costruisci()
	{
		return"CRAFT";
	}
	public String copia()
	{
		return"COPY";
	}
	public String selezionaArma()
	{
		return"SELECT WEAPON";
	}
	public String rotea()
	{
		return"ROTATE";
	}
	public String unisciti()
	{
		return"JOIN";
	}
	public String ip()
	{
		return"IP";
	}
	public String teletrasportaGiu()
	{
		return"TELEPORT DOWN";
	}
	public String teletrasportaSu()
	{
		return"TELEPORT UP";
	}
	public String scegliTeletrasporto()
	{
		return"CHOOSE THE TELEPORT";
	}
	public String scegliPunto()
	{
		return"CHOOSE THE POINT";
	}
	public String cancellando()
	{
		return"ERASING";
	}
	public String copiando()
	{
		return"COPYING";
	}
	public String nuovoUniverso()
	{
		return"NEW UNIVERSE";
	}
	public String terreno()
	{
		return"TERRAIN";
	}
	public String descrizione(Blocco b)
	{
		return b.descrizioneInglese;
	}
	public String nome(Blocco b)
	{
		return b.nomeInglese;
	}
	public String creativa()
	{
		return"CREATIVE";
	}
	public String soppravivenza()
	{
		return"SURVIVAL";
	}
	public String crea()
	{
		return"CREATE";
	}
	public String nome()
	{
		return"NAME";
	}
	public String seme()
	{
		return"SEED";
	}
	public String descrizione()
	{
		StringBuilder sb=new StringBuilder(63*10);
		sb.append("STAR PIXEL IS A SINGLE PLAYER SAMPLE CREATED BY RICCARDO ISOLA&").append(
		"THE MISSION IS TO BUILD A STARSHIP TO EXIT FROM YOUR PLANET AND ESPLORE THE SPACE AND THE DIMENSIONS&").append(
		"&").append(
		"COMANDS:&").append(
		"MOVEMENTS:&").append(
		"   USA THE BOTTON FOR MOVEMENTS&").append(
		"USE ITEMS:&").append(
		"   TOUCH THE SCREEN TO USE YOUR EQUIPPED ITEM ON THAT POINT&").append(
		"BREAK THING:&").append(
		"   LONG PRESS TO DESTROY THE THING YOU ARE TOUCHING WITH YOUR EQUIPPED ITEM&").append(
		"&").append(
		"THERE ARE 3 TYPES OF OBJECTS:&").append(
		"TERRAINS:&").append(
		"   YOU CAN PLACE IT IN THE VOID&").append(
		"SOLIDS:&").append(
		"   YOU CAN PLACE IT ON OTHER TERRAINS&").append(
		"ITEMS:&").append(
		"   THEY ARE OBJECTS TO USE FOR DIFFERENT TASK&").append(
		"&").append(
		"LIST OF ALL OBJECTS")
		;
		return sb.toString();
	}
	public String gioca()
	{
		return"PLAY";
	}
	public String aiuto()
	{
		return"HELP";
	}
}
