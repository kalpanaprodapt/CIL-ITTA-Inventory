import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { FIELD_TASK_CRITICALITY_OPTIONS, FieldTaskCriticality } from './types';
import { validateFieldTask, cleanFieldTaskValues } from './schema';
import { FIELDTASK_DEFAULTS } from './types';
import type { FieldTaskFormValues, FieldTaskInput } from './types';

interface Props {
  initial?: FieldTaskFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: FieldTaskInput) => void;
  onCancel?: () => void;
}

export function FieldTaskForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<FieldTaskFormValues>({ ...FIELDTASK_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof FieldTaskFormValues>(key: K, value: FieldTaskFormValues[K]) {
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
    const result = validateFieldTask(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanFieldTaskValues(values as Record<string, unknown>) as unknown as FieldTaskInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Field Task form">
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
      <FormField label="Work Order Id" htmlFor="workOrderId" error={errors.workOrderId} required={true}>
        <Input
          id="workOrderId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.workOrderId ?? ''}
          onChange={(v) => set('workOrderId', v)}
        />
      </FormField>
      <FormField label="Task Name" htmlFor="taskName" error={errors.taskName} required={true}>
        <Input
          id="taskName"
          value={values.taskName ?? ''}
          maxLength={255}
          onChange={(v) => set('taskName', v)}
        />
      </FormField>
      <FormField label="Sequence" htmlFor="sequence" error={errors.sequence} required={true}>
        <NumberInput
          id="sequence"
          value={values.sequence}
          step={1}
          onChange={(v) => set('sequence', v)}
        />
      </FormField>
      <FormField label="Duration Minutes" htmlFor="durationMinutes" error={errors.durationMinutes} required={false}>
        <NumberInput
          id="durationMinutes"
          value={values.durationMinutes}
          step={1}
          onChange={(v) => set('durationMinutes', v)}
        />
      </FormField>
      <FormField label="Completed On" htmlFor="completedOn" error={errors.completedOn} required={false}>
        <DateTimeInput
          id="completedOn"
          value={values.completedOn ?? ''}
          onChange={(v) => set('completedOn', v)}
        />
      </FormField>
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
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
          options={FIELD_TASK_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as FieldTaskCriticality | undefined)}
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
