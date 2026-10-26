import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { TOPOLOGY_EDGE_LIFECYCLE_STAGE_OPTIONS, TopologyEdgeLifecycleStage } from './types';
import { validateTopologyEdge, cleanTopologyEdgeValues } from './schema';
import { TOPOLOGYEDGE_DEFAULTS } from './types';
import type { TopologyEdgeFormValues, TopologyEdgeInput } from './types';

interface Props {
  initial?: TopologyEdgeFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: TopologyEdgeInput) => void;
  onCancel?: () => void;
}

export function TopologyEdgeForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<TopologyEdgeFormValues>({ ...TOPOLOGYEDGE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof TopologyEdgeFormValues>(key: K, value: TopologyEdgeFormValues[K]) {
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
    const result = validateTopologyEdge(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanTopologyEdgeValues(values as Record<string, unknown>) as unknown as TopologyEdgeInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Topology Edge form">
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
      <FormField label="View Id" htmlFor="viewId" error={errors.viewId} required={true}>
        <Input
          id="viewId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.viewId ?? ''}
          onChange={(v) => set('viewId', v)}
        />
      </FormField>
      <FormField label="Source Node Id" htmlFor="sourceNodeId" error={errors.sourceNodeId} required={true}>
        <Input
          id="sourceNodeId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.sourceNodeId ?? ''}
          onChange={(v) => set('sourceNodeId', v)}
        />
      </FormField>
      <FormField label="Target Node Id" htmlFor="targetNodeId" error={errors.targetNodeId} required={true}>
        <Input
          id="targetNodeId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.targetNodeId ?? ''}
          onChange={(v) => set('targetNodeId', v)}
        />
      </FormField>
      <FormField label="Weight" htmlFor="weight" error={errors.weight} required={false}>
        <NumberInput
          id="weight"
          value={values.weight}
          step={0.0001}
          onChange={(v) => set('weight', v)}
        />
      </FormField>
      <FormField label="Directed" htmlFor="directed" error={errors.directed} required={false}>
        <Checkbox
          id="directed"
          checked={Boolean(values.directed)}
          onChange={(v) => set('directed', v)}
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
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
        />
      </FormField>
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={TOPOLOGY_EDGE_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as TopologyEdgeLifecycleStage | undefined)}
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
