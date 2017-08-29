package com.island.starpixel.lingue;
import com.island.starpixel.blocchi.*;
public class Italiano extends Lingua
{
	public String scudi()
	{
		return"SCUDI";
	}
	public String toccaALungo()
	{
		return"PREMI A LUNGO UN OGGETTO PER DETTAGLI";
	}
	public String inventario()
	{
		return"INVENTARIO";
	}
	public String costruisci()
	{
		return"COSTRUISCI";
	}
	public String copia()
	{
		return"COPIA";
	}
	public String selezionaArma()
	{
		return"SELEZIONA ARMA";
	}
	public String rotea()
	{
		return"ROTEA";
	}
	public String unisciti()
	{
		return"UNISCITI";
	}
	public String ip()
	{
		return"IP";
	}
	public String teletrasportaGiu()
	{
		return"TELETRASPORTA GIU";
	}
	public String teletrasportaSu()
	{
		return"TELETRASPORTA SU";
	}
	public String scegliTeletrasporto()
	{
		return"SCEGLI TELETRASPORTO";
	}
	public String scegliPunto()
	{
		return"SCEGLI PUNTO";
	}
	public String cancellando()
	{
		return"CANCELLANDO";
	}
	public String copiando()
	{
		return"COPIANDO";
	}
	public String nuovoUniverso()
	{
		return"NUOVO UNIVERSO";
	}
	public String terreno()
	{
		return"TERRENO";
	}
	public String descrizione(Blocco b)
	{
		return b.descrizioneItaliano;
	}
	public String nome(Blocco b)
	{
		return b.nomeItaliano;
	}
	public String creativa()
	{
		return"CREATIVA";
	}
	public String soppravivenza()
	{
		return"SOPPRAVIVENZA";
	}
	public String crea()
	{
		return"CREA";
	}
	public String nome()
	{
		return"NOME";
	}
	public String seme()
	{
		return"SEME";
	}
	public String gioca()
	{
		return"GIOCA";
	}
	public String aiuto()
	{
		return"AIUTO";
	}
	public String descrizione()
	{
		StringBuilder sb=new StringBuilder(63*10);
		sb.append("STAR PIXEL É UN GIOCO SANDBOX PER 1 GIOCATORE IDEATO DA RICCARDO ISOLA&").append(
		"L'OBBIETTIVO É DI COSTRUIRE UNA NAVE SPAZIALE PER USCIRE DAL PROPRIO PIANETA E ESPLORARE LA GALASSIA E LE DIMENSIONI&").append(
		"&").append(
		"COMANDI:&").append(
		"MOVIMENTI:&").append(
		"   USA I PULSANTI PER MUOVERTI&").append(
		"UTILIZZO OGGETTI:&").append(
		"   TOCCA LO SCHERMO PER USARE L'OGGETTO CHE HAI EQUIPAGGIATO IN QUEL PUNTO&").append(
		"DISTRUZIONE COSE:&").append(
		"   TIENI PREMUTO PER DISTRUGGERE L'OGGETTO CHE STAI TOCCANDO CON LO STRUMENTO CHE HAI EQUIPAGGIATO&").append(
		"&").append(
		"CI SONO 3 TIPI DI OGGETTI:&").append(
		"TERRENI:&").append(
		"   LI PUOI PIAZZARE NEL VUOTO&").append(
		"SOLIDI:&").append(
		"   LI PUOI PIAZZARE SOPRA I TERRENI&").append(
		"STRUMENTI:&").append(
		"   SONO OGGETTI DA UTILIZZARE PER DIVERSI SCOPI&").append(
		"&").append(
		"LISTA DI TUTTI GLI OGGETTI")
		;
		return sb.toString();
	}
}
