import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { SoftwareImageForm } from './SoftwareImageForm';
import { useCreateSoftwareImage, useUpdateSoftwareImage, useSoftwareImage } from './hooks';
import type { SoftwareImageInput } from './types';

export function SoftwareImageCreatePage() {
  const navigate = useNavigate();
  const create = useCreateSoftwareImage();
  const [error, setError] = useState<string>();

  function onSubmit(input: SoftwareImageInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/software/software-images/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="software-image-new-title">
      <PageHeader id="software-image-new-title" title="New Software Image" />
      <SoftwareImageForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/software/software-images')}
      />
    </section>
  );
}

export function SoftwareImageEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useSoftwareImage(id);
  const update = useUpdateSoftwareImage(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Software Image" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Software Image." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: SoftwareImageInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/software/software-images/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="software-image-edit-title">
      <PageHeader id="software-image-edit-title" title={`Edit ${query.data.code}`} />
      <SoftwareImageForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/software/software-images/${id}`)}
      />
    </section>
  );
}
