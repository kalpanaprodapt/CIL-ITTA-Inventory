import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { DISCOVERY_JOB_JOB_STATE_OPTIONS, DISCOVERY_JOB_CRITICALITY_OPTIONS, DiscoveryJobJobState, DiscoveryJobCriticality } from './types';
import { validateDiscoveryJob, cleanDiscoveryJobValues } from './schema';
import { DISCOVERYJOB_DEFAULTS } from './types';
import type { DiscoveryJobFormValues, DiscoveryJobInput } from './types';

interface Props {
  initial?: DiscoveryJobFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: DiscoveryJobInput) => void;
  onCancel?: () => void;
}

export function DiscoveryJobForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<DiscoveryJobFormValues>({ ...DISCOVERYJOB_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof DiscoveryJobFormValues>(key: K, value: DiscoveryJobFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateDiscoveryJob(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanDiscoveryJobValues(values as Record<string, unknown>) as unknown as DiscoveryJobInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Discovery Job form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Profile Id" htmlFor="profileId" error={errors.profileId} required={true}>
        <Input
          id="profileId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.profileId ?? ''}
          onChange={(v) => set('profileId', v)}
        />
      </FormField>
      <FormField label="Started At" htmlFor="startedAt" error={errors.startedAt} required={false}>
        <DateTimeInput
          id="startedAt"
          value={values.startedAt ?? ''}
          onChange={(v) => set('startedAt', v)}
        />
      </FormField>
      <FormField label="Finished At" htmlFor="finishedAt" error={errors.finishedAt} required={false}>
        <DateTimeInput
          id="finishedAt"
          value={values.finishedAt ?? ''}
          onChange={(v) => set('finishedAt', v)}
        />
      </FormField>
      <FormField label="Job State" htmlFor="jobState" error={errors.jobState} required={false}>
        <Select
          id="jobState"
          value={values.jobState ?? ''}
          options={DISCOVERY_JOB_JOB_STATE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('jobState', (v || undefined) as DiscoveryJobJobState | undefined)}
        />
      </FormField>
      <FormField label="Assets Found" htmlFor="assetsFound" error={errors.assetsFound} required={false}>
        <NumberInput
          id="assetsFound"
          value={values.assetsFound}
          step={1}
          onChange={(v) => set('assetsFound', v)}
        />
      </FormField>
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={DISCOVERY_JOB_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as DiscoveryJobCriticality | undefined)}
        />
      </FormField>
      <FormField label="Cost Center" htmlFor="costCenter" error={errors.costCenter} required={false}>
        <Input
          id="costCenter"
          value={values.costCenter ?? ''}
          maxLength={255}
          onChange={(v) => set('costCenter', v)}
        />
      </FormField>
      <FormField label="Notes" htmlFor="notes" error={errors.notes} required={false}>
        <TextArea
          id="notes"
          value={values.notes ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('notes', v)}
        />
      </FormField>
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
