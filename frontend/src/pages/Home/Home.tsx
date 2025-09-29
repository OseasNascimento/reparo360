// src/components/Home.tsx
import React from 'react'
import { Swiper, SwiperSlide } from 'swiper/react'
import { Navigation, Autoplay } from 'swiper/modules'
import { Link } from 'react-router-dom'
import 'swiper/css'
import 'swiper/css/navigation'

export default function Landing() {
  const serviços = [
    { img: '/assets/fogao.jpg', title: 'Conserto de Fogão', desc: 'Pronto para cozinhar! Chamadas flamejantes resolvidas.' },
    { img: '/assets/coifa.jpg', title: 'Conserto de Coifa', desc: 'Elimine odores e vapores com coifa novinha.' },
    { img: '/assets/geladeira.jpg', title: 'Conserto de Geladeira', desc: 'Refrigeração perfeita sem barulhos estranhos.' },
    { img: '/assets/micro.jpg', title: 'Conserto de Micro-ondas', desc: 'Sempre pronto para esquentar em segundos.' },
    { img: '/assets/maquina.jpg', title: 'Conserto de Máquina', desc: 'Lavagem impecável e sem vazamentos.' },
  ]

  return (
    <div className="flex flex-col min-h-screen font-body text-gray-900">
      {/* Hero Section */}
      <section
        id="início"
        className="relative flex items-center justify-center min-h-[600px]"
        style={{
          backgroundImage: "url('/assets/hero-bg.jpg')",
          backgroundSize: 'cover',
          backgroundPosition: 'center',
          backgroundColor: '#29b6f6',
          backgroundBlendMode: 'multiply',
        }}
      >
        <div className="text-center px-6 z-10">
          <h1 className="font-heading text-white text-[48px] font-bold max-w-[700px] mx-auto">
            Sua casa funcionando 100%, sempre.
          </h1>
          <p className="text-[24px] text-gray-200 mt-5">
            Agende online em segundos
          </p>
          <div className="mt-10 flex justify-center space-x-4">
            <Link to="/agendamentos">
              <button
                className="bg-[#25D366] text-white px-10 py-4 rounded-full font-heading text-lg shadow-lg
                           hover:bg-[#e65c00] transition"
              >
                Agende seu conserto
              </button>
            </Link>
            <a
              href="https://wa.me/5511999990000"
              target="_blank"
              rel="noopener noreferrer"
              className="bg-[#25D366] text-white px-10 py-4 rounded-full font-heading text-lg shadow-lg
                         hover:bg-[#21BE5C] transition"
            >
              WhatsApp
            </a>
          </div>
        </div>
        <div className="absolute inset-0 bg-black opacity-30"></div>
      </section>

      {/* Serviços (Carousel) */}
      <section id="serviços" className="bg-white py-20">
        <div className="max-w-[1200px] mx-auto px-6">
          <h2 className="font-heading text-[36px] text-center text-gray-900 font-semibold mb-8">
            Nossos Serviços
          </h2>

          <Swiper
            modules={[Navigation, Autoplay]}
            navigation
            loop
            autoplay={{ delay: 4000 }}
            slidesPerView={1}
            spaceBetween={20}
            breakpoints={{
              640: { slidesPerView: 2 },
              1024: { slidesPerView: 3 }
            }}
          >
            {serviços.map(s => (
              <SwiperSlide key={s.title}>
                <div className="bg-gray-100 rounded-lg overflow-hidden shadow-md">
                  <img src={s.img} alt={s.title} className="w-full h-40 object-cover" />
                  <div className="p-4">
                    <h3 className="font-heading text-primary text-xl">{s.title}</h3>
                    <p className="mt-2 text-base text-gray-700">{s.desc}</p>
                    <a href="#" className="inline-block mt-4 text-[#FF6A00] font-bold hover:underline">
                      Saiba mais →
                    </a>
                  </div>
                </div>
              </SwiperSlide>
            ))}
          </Swiper>
        </div>
      </section>

      {/* Fale com a gente (Formulário) */}
      <section id="contato" className="py-20 bg-white">
        <div className="max-w-[1200px] mx-auto px-6 grid grid-cols-1 lg:grid-cols-2 gap-8 items-center">
          <div className="relative h-[500px] rounded-lg overflow-hidden">
            <img
              src="/assets/contact-bg.jpg"
              alt="Técnico atendendo"
              className="absolute inset-0 w-full h-full object-cover"
            />
            <div className="absolute inset-0 bg-black opacity-40"></div>
            <div className="relative z-10 text-white px-8">
              <h2 className="font-heading text-[32px] mb-4">Fale com a gente</h2>
              <p className="text-lg">
                Entre em contato pelo formulário e receba um atendimento rápido e personalizado.
              </p>
            </div>
          </div>

          <form className="space-y-6 bg-gray-100 p-8 rounded-lg shadow-lg">
            <div>
              <label className="block mb-2 font-semibold text-gray-800">Nome *</label>
              <input
                type="text"
                className="w-full p-3 text-base border border-gray-300 rounded-md"
                required
              />
            </div>
            <div>
              <label className="block mb-2 font-semibold text-gray-800">E-mail *</label>
              <input
                type="email"
                className="w-full p-3 text-base border border-gray-300 rounded-md"
                required
              />
            </div>
            <div>
              <label className="block mb-2 font-semibold text-gray-800">Telefone *</label>
              <input
                type="tel"
                className="w-full p-3 text-base border border-gray-300 rounded-md"
                placeholder="(11) 99999-0000"
                required
              />
            </div>
            <div>
              <label className="block mb-2 font-semibold text-gray-800">Mensagem</label>
              <textarea
                rows={4}
                className="w-full p-3 text-base border border-gray-300 rounded-md"
              ></textarea>
            </div>
            <div className="flex items-center space-x-2">
              <input type="checkbox" id="policy" className="w-4 h-4" required />
              <label htmlFor="policy" className="text-sm text-gray-700">
                Eu concordo com a <a href="#" className="text-primary hover:underline">Política de Privacidade</a>
              </label>
            </div>
            <button
              type="submit"
              className="w-full bg-primary text-white py-3 rounded-md font-heading hover:bg-[#004480] transition"
            >
              Enviar
            </button>
            <p className="text-sm italic text-gray-600 text-center">
              Responderemos em até 2 horas
            </p>
          </form>
        </div>
      </section>

      {/* Depoimentos e Footer seguem iguais ao seu template original */}

    </div>
  )
}
