import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { WORK_ORDER_WORK_STATE_OPTIONS, WORK_ORDER_LIFECYCLE_STAGE_OPTIONS, WORK_ORDER_CRITICALITY_OPTIONS, WorkOrderWorkState, WorkOrderLifecycleStage, WorkOrderCriticality } from './types';
import { validateWorkOrder, cleanWorkOrderValues } from './schema';
import { WORKORDER_DEFAULTS } from './types';
import type { WorkOrderFormValues, WorkOrderInput } from './types';

interface Props {
  initial?: WorkOrderFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: WorkOrderInput) => void;
  onCancel?: () => void;
}

export function WorkOrderForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<WorkOrderFormValues>({ ...WORKORDER_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof WorkOrderFormValues>(key: K, value: WorkOrderFormValues[K]) {
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
    const result = validateWorkOrder(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanWorkOrderValues(values as Record<string, unknown>) as unknown as WorkOrderInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Work Order form">
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
      <FormField label="Work Order Number" htmlFor="workOrderNumber" error={errors.workOrderNumber} required={true}>
        <Input
          id="workOrderNumber"
          value={values.workOrderNumber ?? ''}
          maxLength={255}
          onChange={(v) => set('workOrderNumber', v)}
        />
      </FormField>
      <FormField label="Work Type" htmlFor="workType" error={errors.workType} required={true}>
        <Input
          id="workType"
          value={values.workType ?? ''}
          maxLength={255}
          onChange={(v) => set('workType', v)}
        />
      </FormField>
      <FormField label="Assigned Technician Id" htmlFor="assignedTechnicianId" error={errors.assignedTechnicianId} required={false}>
        <Input
          id="assignedTechnicianId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.assignedTechnicianId ?? ''}
          onChange={(v) => set('assignedTechnicianId', v)}
        />
      </FormField>
      <FormField label="Due Date" htmlFor="dueDate" error={errors.dueDate} required={false}>
        <DateInput
          id="dueDate"
          value={values.dueDate ?? ''}
          onChange={(v) => set('dueDate', v)}
        />
      </FormField>
      <FormField label="Work State" htmlFor="workState" error={errors.workState} required={false}>
        <Select
          id="workState"
          value={values.workState ?? ''}
          options={WORK_ORDER_WORK_STATE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('workState', (v || undefined) as WorkOrderWorkState | undefined)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={WORK_ORDER_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as WorkOrderLifecycleStage | undefined)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
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
          options={WORK_ORDER_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as WorkOrderCriticality | undefined)}
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
