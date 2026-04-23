import HeroSection from "@/components/main/HeroSection";
import TrendingTopics from "@/components/main/TrendingTopics";
import CategorySection from "@/components/main/CategorySection";

export default function HomePage() {
    return (
        <div className="page-container section-gap">
            <HeroSection />
            <TrendingTopics />
            <CategorySection />
        </div>
    );
}