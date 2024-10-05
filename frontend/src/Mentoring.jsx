import './assets/css/Home.css';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';

function App() {

    const category = ['추천 작품', '인기 작가', 'New'];
    const exhibition = ['현재 진행중인 전시', '오픈 예정 전시'];


  return (
    <>
    <div style={{marginTop: '30px', display:'flex', flexDirection:'column', alignItems:'center'}}>
        <div style={{marginBottom:'62px'}}>
            <h2></h2> 
            <div>
                <div class="img_art"><img src="https://via.placeholder.com/356x356" alt=""/></div>
                <div class="img_art"><img src="https://via.placeholder.com/356x356" alt=""/></div>
                <div class="img_art"><img src="https://via.placeholder.com/356x356" alt=""/></div>
            </div>
        </div>
    </div>
    </>
  )
}

export default App
