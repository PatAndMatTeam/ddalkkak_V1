export type TopicSide = "A" | "B";

export type TopicOption = {
    id: TopicSide;
    title: string;
    imageUrl?: string | null;
    voteCount: number;
};

export type DiscussionComment = {
    id: number;
    topicId?: number;
    writer: string;
    content: string;
    side: TopicSide | null;
    createdAt: string;
};

export type AnalysisPost = {
    id: number;
    topicId?: number;
    topicTitle?: string;
    topicSlug?: string;
    title: string;
    writer: string;
    summary: string;
    content?: string;
    createdAt: string;
};

export type Topic = {
    id: number;
    slug: string;
    category: string;
    categoryName?: string;
    title: string;
    description: string;
    writer: string;
    createdAt: string;
    viewCount: number;
    optionA: TopicOption;
    optionB: TopicOption;
    totalVotes: number;
    commentCount?: number;
    analysisCount?: number;
    myVote?: TopicSide | null;
};