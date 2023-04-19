import '../styles/NavigationBar.css'
import { useState, useEffect } from "react";

export default function NavigationBar() {
    const [isScrolled, setScrolled] = useState(false);

    useEffect(() => {
        function handleScroll() {
            const scrollTop = document.documentElement.scrollTop;

            if (scrollTop === 0) {
                setScrolled(false);
            } else {
                setScrolled(true);
            }
        }

        window.addEventListener("scroll", handleScroll);
        return () => window.removeEventListener("scroll", handleScroll);
    }, []);

    return (
       <div className={`navigation-bar ${isScrolled ? "scrolled" : ""}`}>
        </div>
    );
}