import './assets/css/Home.css';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';

function App() {

    const category = ['추천 작품', '인기 작가', 'New'];
    const exhibition = ['현재 진행중인 전시', '오픈 예정 전시'];


  return (
    <>

    <div id="banner">
        <Swiper
        spaceBetween={50}
        slidesPerView={1}
        pagination={{ clickable: true }}
        >
        <SwiperSlide><img src="https://via.placeholder.com/1920x550" alt="" /></SwiperSlide>
        <SwiperSlide><img src="https://via.placeholder.com/1920x550" alt="" /></SwiperSlide>
        <SwiperSlide><img src="https://via.placeholder.com/1920x550" alt="" /></SwiperSlide>
        <SwiperSlide><img src="https://via.placeholder.com/1920x550" alt="" /></SwiperSlide>
        </Swiper>
        
    </div>

    <div style={{marginTop: '30px', display:'flex', flexDirection:'column', alignItems:'center'}}>
        {category.map((homeTitle) => (
            <div style={{marginBottom:'62px'}}>
                <h2>{homeTitle}&nbsp;<img src='./src/assets/img/right-chevron.svg'/></h2> 
                <div>
                    <div class="img_art"><img src="https://via.placeholder.com/356x356" alt=""/></div>
                    <div class="img_art"><img src="https://via.placeholder.com/356x356" alt=""/></div>
                    <div class="img_art"><img src="https://via.placeholder.com/356x356" alt=""/></div>
                </div>
            </div>
        ))}

        {exhibition.map((exhTitle) => (
            <div style={{marginBottom:'62px'}}>
                <h2>{exhTitle}&nbsp;<img src='./src/assets/img/right-chevron.svg'/></h2>
                <div>
                    <div class="img_exh"><img src="https://via.placeholder.com/261x175" alt=""/></div>
                    <div class="img_exh"><img src="https://via.placeholder.com/261x175" alt=""/></div>
                    <div class="img_exh"><img src="https://via.placeholder.com/261x175" alt=""/></div>
                    <div class="img_exh"><img src="https://via.placeholder.com/261x175" alt=""/></div>
                </div>
            </div>
        ))}
    </div>
    </>
  )
}

export default App
