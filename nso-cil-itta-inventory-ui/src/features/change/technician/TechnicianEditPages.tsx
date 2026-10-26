import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { TechnicianForm } from './TechnicianForm';
import { useCreateTechnician, useUpdateTechnician, useTechnician } from './hooks';
import type { TechnicianInput } from './types';

export function TechnicianCreatePage() {
  const navigate = useNavigate();
  const create = useCreateTechnician();
  const [error, setError] = useState<string>();

  function onSubmit(input: TechnicianInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/change/technicians/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="technician-new-title">
      <PageHeader id="technician-new-title" title="New Technician" />
      <TechnicianForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/change/technicians')}
      />
    </section>
  );
}

export function TechnicianEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useTechnician(id);
  const update = useUpdateTechnician(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Technician" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Technician." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: TechnicianInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/change/technicians/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="technician-edit-title">
      <PageHeader id="technician-edit-title" title={`Edit ${query.data.code}`} />
      <TechnicianForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/change/technicians/${id}`)}
      />
    </section>
  );
}
