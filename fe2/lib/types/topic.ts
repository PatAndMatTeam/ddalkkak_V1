export type TopicOption = {
    id: "A" | "B";
    title: string;
    image: string;
    voteCount: number;
};

export type DiscussionComment = {
    id: number;
    writer: string;
    content: string;
    side: "A" | "B";
    createdAt: string;
};

export type AnalysisPost = {
    id: number;
    title: string;
    writer: string;
    summary: string;
    content: string;
    createdAt: string;
};

export type Topic = {
    id: number;
    slug: string;
    category: string;
    title: string;
    description: string;
    writer: string;
    createdAt: string;
    viewCount: number;
    optionA: TopicOption;
    optionB: TopicOption;
    discussions: DiscussionComment[];
    analyses: AnalysisPost[];
};