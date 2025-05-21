import Headers from "../header/header";
import Footer from "../footer/footer"

function DefaultLayout({children}){
    return (
        <div>
            <Headers/>
            <div className="main-content-web">
            {children}
            </div>
            <Footer/>
        </div>
    );
}

export default DefaultLayout;